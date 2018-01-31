var authorID = 0;
var authorRole = 3;
var aid;

function updateComments(commentList, authorID, authorRole){
    var commentListDiv = $('#commentList');
    commentListDiv.html('');
    commentList.forEach(function(comment){
        var cd = $('<div id="comment' + comment.cid + '" class="commentBox"></div>');
        cd.append('<p><span>@' + comment.author + ' </span>' + '<span>  Created Time:' + comment.createdTime +'</span></p>');
        cd.append('<p>' + comment.content + '</p>');
        if(authorRole == 1 || authorID == comment.authorID){
            // show delete button
            cd.append('<button class="btn btn-sm btn-outline-danger" onclick="deleteComment(' + comment.cid + ')">Delete</button>');
        }
        cd.append('<hr/>')
        commentListDiv.append(cd);
    });
}

function deleteComment(cid){
    $.ajax({
        url: 'api/commentDelete',
        data: {cid: cid},
        method: 'post',
        dataType: 'json',
        success: function(ret) {
            if (ret.result === "success") {
                $('#comment'+cid).hide();
                $('#failedBanner').hide();
            } else {
                $('#failedBanner').show();
                if(!ret.reason)
                    ret.reason = "Unknown reason";
                $('#failedMessage').text(ret.reason);
            }
        },
        error: function(ret){
            console.log(ret);
            $('#failedBanner').show();
            if(!ret.reason)
                ret.reason = "Unknown reason";
            $('#failedMessage').text(ret.reason);
        }
    })
}

function getCommentList(){
    $.ajax({
        url:'api/commentList',
        data:{aid:aid},
        method:'get',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success:function(ret){
            var commentListDiv = $('#commentList');
            if(ret.result != 'fail'){
                updateComments(ret, authorID, authorRole);
            } else {
                if(!ret.reason){
                    ret.reason = "Cannot get comments currently."
                }
                commentListDiv.html('<h3>' + ret.reason + '</h3>')
            }

        },
        error:function(ret){
            $('#commentList').html('<h3>No response from server, cannot add comments now.</h3>')
        }
    });
}

$(function () {
    var searchParams = new URLSearchParams(window.location.search);
    aid = searchParams.get('aid');

    $.ajax({
        url: 'api/articleView',
        data: {aid: aid},
        method: 'post',
        dataType: 'json',
        async: false,
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

                authorID = ret.authorID;
                authorRole = ret.authorRole;

                if(authorID !== 0){
                    $('#commentAdd').show();
                }
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


    getCommentList();

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

    $('#btnAddComment').click(function(){
        $.ajax({
            url: 'api/commentAdd',
            data: {aid: aid, content: $('#commentContent').val()},
            method: 'post',
            dataType: 'json',
            success: function(ret){
                if(ret.result === "success") {
                    getCommentList();
                } else {
                    $('#failedBanner').show();
                    if(!ret.reason)
                        ret.reason = "Unknown reason";
                    $('#failedMessage').text(ret.reason);
                }
            },
            error: function(ret){
                $('#failedBanner').show();
                if(!ret.reason)
                    ret.reason = "Unknown reason";
                $('#failedMessage').text(ret.reason);
            }
        })
    });
});
