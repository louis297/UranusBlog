function getCommentList(own) {
    var params = {own: own=='true'};
    $.ajax({
        url: '/api/commentList',
        method: 'get',
        data: params,
        dataType: 'json',
        success: function(ret) {
            parsedData = [];
            console.log(ret);
            if(ret.result != 'fail') {
                updateArticles(ret);
                lock = false;
            } else {
                // todo: provide link to create blog if list is empty
                $('#articleList').html('<h1>No articles found.</h1>');
                lock = false;
            }
        },
        error: function(ret){
            console.log('getArticleList ajax fail')
            console.log(ret)
        }
    });
}





$(function () {
    var searchParams = new URLSearchParams(window.location.search);
    var aid = searchParams.get('aid');

    $.ajax({
        url: '/api/articleView',
        data: {aid: aid},
        method: 'post',
        dataType: 'json',
        success: function(ret) {
            if (ret.result === "success") {

                $('#title').text(ret.title);
                $('#content').text(ret.content);
                $('#author').text("Author: " + ret.authorName);
                $('#modify').text("Modified on: " + ret.modified_time);

                $('#title').show();
                $('#content').show();
                $('#author').show();
                $('#modify').show();
                $('#main').show();

                if (ret.isOwnArticle) {
                    $('#btnModify').show();
                    $('#btnDelete').show();

                }
            } else {
                $('#fail_title').show();
                $('#fail_reason').text(ret.reason);
                $('#fail_reason').show();
            }
        },
        error: function(ret){
            $('#fail_title').show();
            $('#fail_reason').text(ret.reason);
            $('#fail_reason').show();
        }
    });

    $.ajax({
        url:'api/commentList',
        data:{uid:uid},
        method:'get',
        dataType:'json',
        success:function(){

        },
        error:function(){

        }
    })


    $('#btnModify').click(function(){
        window.location.href='article_edit.html?aid='+aid;
    });


    $('#btnConfirmDeletion').click(function(){
        $.ajax({
            url: 'api/articleDelete',
            data: {'aid': aid},
            method: 'post',
            dataType: 'json',
            success: function(ret){
                // popup
                if(ret.result==="success") {
                    $('#succeededDeletion').modal();
                } else {
                    alert(ret.reason);
                }
            },
            error: function(){

            }

        })
    });
});
