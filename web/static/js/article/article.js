





$(function () {
    var searchParams = new URLSearchParams(window.location.search);
    var aid = searchParams.get('aid');

    $.ajax({
        url: '/api/articleView',
        data: {aid: aid},
        method: 'post',
        dataType: 'json',
        success: function(ret){
            $('#title').text(ret.title);
            $('#content').text(ret.content);
            $('#author').text("Author: " + ret.authorName);
            $('#modify').text("Modified on: " + ret.modified_time);

            $('#title').show();
            $('#content').show();
            $('#author').show();
            $('#modify').show();
            $('#main').show();

            if(ret.isOwnArticle){
                $('#btnModify').show();
                $('#btnDelete').show();

            }
        },
        error: function(ret){
            $('#fail_title').show();
            $('#fail_reason').text(ret.reason);
            $('#fail_reason').show();
        }
    });


    $('#btnModify').click(function(){

    });


    $('#btnDelete').click(function(){
        $.ajax({
            url: '/api/articleDelete',
            data: {'aid': aid},
            method: 'post',
            success: function(){

            },
            error: function(){

            }

        })
    });
});
