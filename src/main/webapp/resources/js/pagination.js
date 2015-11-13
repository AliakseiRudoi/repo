$(function() {
    $('#light-theme').pagination({
     pages: '${paginNum}',
        itemsOnPage: 3,
        cssStyle: 'light-theme',
        hrefTextPrefix: '',
        currentPage: '${pageNumber}'
    });
});