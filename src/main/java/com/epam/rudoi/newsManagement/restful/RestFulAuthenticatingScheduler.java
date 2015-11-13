package com.epam.rudoi.newsManagement.restful;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.epam.rudoi.newsManagement.restful.util.FeedParserGroup;
import com.epam.rudoi.newsManagement.restful.util.PrevFeedUriGetter;
import com.epam.rudoi.newsportal.entity.Author;
import com.epam.rudoi.newsportal.entity.FaceBookStatus;
import com.epam.rudoi.newsportal.entity.News;
import com.epam.rudoi.newsportal.entity.NewsManagementVO;
import com.epam.rudoi.newsportal.entity.Tag;
import com.epam.rudoi.newsportal.exeption.ServiceExeption;
import com.epam.rudoi.newsportal.service.INewsManagementService;

@Component
@Path("/auth")
public class RestFulAuthenticatingScheduler {

	public final static String RESPONSE_TYPE = "code";
	public final static String CLIENT_ID = "907960632592250";
	public final static String REDIRECT_URI = "http://localhost:8085/news-admin/rest/auth/getAccessToken";
	public final static String STATE = "987654321";
	public final static String CLIENT_SECRET = "08ffabea58c59c813eb7ae4236c8d09a";
	public final static String REDIRECT_URI2 = "http://localhost:8085/news-admin/rest/auth/accessTokenResult";
	public final static String REDIRECT_URI3 = "https%3A%2F%2Fwww.google.by";

	public final static String ANDROIDAUTHORITY = "androidauthority";

	public final static String REDIRECT_URL = "http://localhost:8085/news-admin/news/";
	public final static String FACEBOOK_URL = "https://graph.facebook.com/";

	@Autowired
	private INewsManagementService serviceManager;

	FaceBookStatus faceBookStatus = new FaceBookStatus();

	@GET
	@Path("/tryAuth")
	@Produces(MediaType.APPLICATION_JSON)
	public Response tryToGeAccessToken() throws OAuthSystemException, URISyntaxException {
		OAuthClientRequest request = null;
		request = OAuthClientRequest.authorizationProvider(OAuthProviderType.FACEBOOK).setClientId(CLIENT_ID)
				.setRedirectURI(REDIRECT_URI).buildQueryMessage();
		URI uri = new URI(request.getLocationUri());
		return Response.temporaryRedirect(uri).build();
	}

	// 300 000 - 5 min
	@Scheduled(fixedDelay = 10000)
	public void tryShadow() throws OAuthSystemException, OAuthProblemException, ServiceExeption {
		System.out.println(new Date());

		if (faceBookStatus.getAccessToken() != null) {
			FeedParserGroup feedParseGroup = new FeedParserGroup();
			OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
			facebookFeedManager(feedParseGroup, oAuthClient, faceBookStatus.getAccessToken(), ANDROIDAUTHORITY);
		}
	}

	@GET
	@Path("/getAccessToken")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAndManageFeed(@QueryParam("code") String code)
			throws OAuthSystemException, OAuthProblemException, ServiceExeption, URISyntaxException {

		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

		OAuthClientRequest request = OAuthClientRequest.tokenProvider(OAuthProviderType.FACEBOOK).setClientId(CLIENT_ID)
				.setClientSecret(CLIENT_SECRET).setRedirectURI(REDIRECT_URI)
				.setScope("public_profile,email,user_friends").setCode(code).buildQueryMessage();

		GitHubTokenResponse oAuthResponse = oAuthClient.accessToken(request, GitHubTokenResponse.class);

		faceBookStatus.setAccessToken(oAuthResponse.getAccessToken());
		faceBookStatus.setExpiresIn(oAuthResponse.getExpiresIn());

		URI uri = new URI(REDIRECT_URL);

		return Response.temporaryRedirect(uri).build();
	}

	private void facebookFeedManager(FeedParserGroup feedParseGroup, OAuthClient oAuthClient, String accessToken,
			String AuthenticatingId) throws OAuthSystemException, OAuthProblemException, ServiceExeption {

		OAuthResourceResponse jsonAuthorAndTag = getAuthorAndTagJson(oAuthClient, accessToken, AuthenticatingId);
		String authorAndTagName = authorAndTagBuilder(jsonAuthorAndTag.getBody());
		List<Author> authorsList = buildAuthorsList(authorAndTagName);
		List<Tag> tagsList = buildTagsList(authorAndTagName);

		OAuthResourceResponse groupFeedJson = getFeedJson(oAuthClient, accessToken, AuthenticatingId);

		groupFeedManager(groupFeedJson, oAuthClient, feedParseGroup, tagsList, authorsList);
	}

	private void groupFeedManager(OAuthResourceResponse groupFeedJson, OAuthClient oAuthClient,
			FeedParserGroup feedParseGroup, List<Tag> tagsList, List<Author> authorsList) throws ServiceExeption {

		List<NewsManagementVO> newsVOList = null;

		Boolean getPrevFeedflag = false;
		Boolean interuptFlag = false;
		OAuthResourceResponse currentJson = null;
		while (!interuptFlag) {
			if (currentJson == null) {
				currentJson = groupFeedJson;
			}

			if (!getPrevFeedflag) {
				newsVOList = feedParseGroup.parseFeedGpoup(currentJson.getBody());
			}

			int iter = 0;
			for (NewsManagementVO newsManagementVO : newsVOList) {
				iter++;
				if (iter == newsVOList.size()) {
					getPrevFeedflag = true;
				} else {
					getPrevFeedflag = false;
				}

				if (!checkFacebookIdExistInDB(newsManagementVO.getNews().getFacebookId())) {
					System.out.println(
							"FIX checking " + checkFacebookIdExistInDB(newsManagementVO.getNews().getFacebookId()));
					System.out.println("adding news to DB " + getPrevFeedflag);
					addNewsFromFBFeed(newsManagementVO, authorsList, tagsList);

				} else {
					getPrevFeedflag = false;
					System.out.println("NOT adding news to DB " + getPrevFeedflag);
					break;
				}
			}
			if (getPrevFeedflag) {
				String prevFeedUri = PrevFeedUriGetter.parseFeedJsonToGetPrevUri(currentJson.getBody());
				OAuthResourceResponse prevGroupJson = PrevFeedUriGetter.getPrevFeedJson(prevFeedUri, oAuthClient);
				currentJson = prevGroupJson;
				newsVOList = feedParseGroup.parseFeedGpoup(prevGroupJson.getBody());
				continue;
			} else {
				interuptFlag = true;
				System.out.println("END WHILE " + getPrevFeedflag + " " + interuptFlag);
				break;
			}
		}

	}

	private OAuthResourceResponse getAuthorAndTagJson(OAuthClient oAuthClient, String accessToken, String groupId)
			throws OAuthSystemException, OAuthProblemException {
		OAuthClientRequest bearerClientRequest3 = new OAuthBearerClientRequest("https://graph.facebook.com/" + groupId)
				.setAccessToken(accessToken).buildQueryMessage();
		OAuthResourceResponse resourceResponseWithNews3 = oAuthClient.resource(bearerClientRequest3,
				OAuth.HttpMethod.GET, OAuthResourceResponse.class);
		return resourceResponseWithNews3;
	}

	private List<Tag> buildTagsList(String authorAndTagName) throws ServiceExeption {
		Tag tag = new Tag(authorAndTagName);
		NewsManagementVO newsManagementVO = new NewsManagementVO(tag);
		List<Tag> tagsList = new ArrayList<Tag>();
		tagsList.add(serviceManager.readTagByName(newsManagementVO));
		return tagsList;
	}

	private List<Author> buildAuthorsList(String authorAndTagName) throws ServiceExeption {
		Author author = new Author(authorAndTagName);
		NewsManagementVO newsManagementVO = new NewsManagementVO(author);
		List<Author> authorsList = new ArrayList<Author>();
		authorsList.add(serviceManager.readAuthorByName(newsManagementVO));
		return authorsList;
	}

	//TODO URL
	private OAuthResourceResponse getFeedJson(OAuthClient oAuthClient, String accessToken, String groupId)
			throws OAuthSystemException, OAuthProblemException {
		OAuthClientRequest bearerClientRequest2 = new OAuthBearerClientRequest(
				FACEBOOK_URL + groupId + "/feed").setAccessToken(accessToken).buildQueryMessage();
		OAuthResourceResponse resourceResponseWithNews = oAuthClient.resource(bearerClientRequest2,
				OAuth.HttpMethod.GET, OAuthResourceResponse.class);
		return resourceResponseWithNews;
	}

	private void addNewsFromFBFeed(NewsManagementVO newsManagementVO, List<Author> authorsList, List<Tag> tagsList)
			throws ServiceExeption {
		newsManagementVO.getNews().setVersion(1L);
		newsManagementVO.getNews().setAuthorsList(authorsList);
		newsManagementVO.getNews().setTagsList(tagsList);

		serviceManager.addNews(newsManagementVO);

	}

	private String authorAndTagBuilder(String authorAndTagJson) {
		String authorAndTagName = null;

		JsonFactory jfactory = new JsonFactory();
		JsonParser jParser = null;
		try {
			jParser = jfactory.createJsonParser(authorAndTagJson);

			while (jParser.nextToken() != JsonToken.END_OBJECT) {

				String fieldname = jParser.getCurrentName();
				if ("name".equals(fieldname)) {
					jParser.nextToken();
					authorAndTagName = jParser.getText();
				}
			}

		} catch (Exception e1) {

			e1.printStackTrace();
		}
		return authorAndTagName;
	}

	private Boolean checkFacebookIdExistInDB(String facebookId) {
		Boolean flag = false;
		News news = null;
		try {
			news = serviceManager.readNewsByFacebookId(facebookId);
			if (news != null) {
				flag = true;
			}
		} catch (ServiceExeption e) {
			e.printStackTrace();
		}
		return flag;
	}

}
