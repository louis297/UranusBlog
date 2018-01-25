function updateArticles(articleList){
    var i=0;
    articleList.forEach(function(article){
        var ad = $('<div id="article' + i + '" class="articleBox"></div>')
        ad.append('<div class="articleTitle" id="articleTitle' + i +'">' +
            article.title +
            '</div>');
        ad.append('<div id="author"' + i + '>' +
            article.author +
            '</div>')
        ad.append('<div id="create_time"' + i + '>' +
            article.createTime +
            '</div>')
        ad.append('<div id="modify_time"' + i + '>' +
            article.modifiedTime +
            '</div>')
        ad.append('<div class="articleContent" id="articleContent' + i +'">' +
            article.content +
            '</div>');

        i++;
        $('#articleList').append(ad)
    });
}

function loginAction(){
    var dataStr = 'Username=' + $();
    $.ajax({
        url: '/login',
        method: 'post',
        data: dataStr,
        success: function() {

        }
    })
}

$(function () {
    var loginBtn = $('#loginButton');
    loginBtn.click(loginAction);


    var articleList = getArticleList(true)
    console.log(articleList)
    updateArticles(articleList)

})