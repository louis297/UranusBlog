function updateArticles(articleList){
    var i=0;
    articleList.forEach(function(article){
        var ad = $('<div id="article' + i + '"></div>')
        ad.append('<div class="articleTitle" id="articleTitle' + i +'">' +
            article.title +
            '</div>');
        ad.append('<div class="articleContent" id="articleContent' + i +'">' +
            article.content +
            '</div>');
        
        i++;
        $('#articleList').append(ad)
    });
}

$(function () {
    var articleList = getArticleList(true)
    updateArticles(articleList)

})