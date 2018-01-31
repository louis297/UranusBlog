$(function(){
    var searchParams = new URLSearchParams(window.location.search);
    var aid = searchParams.get('aid');
    $.ajax({
        url:'api/articleView',
        method:'post',
        data:{aid:aid},
        dataType:'json',
        success:function(ret){
            if(ret.result === 'success'){
                $('#title').val(ret.title);
                $('#content').val(ret.content);
                $('#postTime').val(ret.post_time);
                if(ret.isPrivate){
                    $('#isPrivate').prop('checked', true);
                }
            } else {
                $('#failedBanner').show();
                if(!!ret.reason){
                    $('#failedMessage').text(ret.reason);
                } else {
                    $('#failedMessage').text("Unknown server error.");
                }
            }
        },
        error:function(ret){
            $('#failedBanner').show();
            if(!!ret.reason){
                $('#failedMessage').text(ret.reason);
            } else {
                $('#failedMessage').text("Unknown server error.");
            }
        }

    });
    $('#btnSubmit').click(function(){
        var data={};
        data.title = $('#title').val();
        data.content = $('#content').val();
        var postTime = $('#postTime').val();
        if ((postTime.match(/:/g) || []).length === 1) {
            postTime += ":00";
        }
        data.aid = aid;
        postTime = postTime.replace('T', ' ');
        data.postTime = postTime;
        data.isPrivate = $('#isPrivate').is(':checked');
        console.log(data);
        $.ajax({
            url:'api/articleModify',
            method:'post',
            data:data,
            dataType:'json',
            success:function(ret){
                if(ret.result === 'success'){
                    window.location = 'article.html?aid=' + ret.aid;
                } else {
                    $('#failedBanner').show();
                    if(!!ret.reason){
                        $('#failedMessage').text(ret.reason);
                    } else {
                        $('#failedMessage').text("Unknown server error.");
                    }
                }
            },
            error:function(ret){
                $('#failedBanner').show();
                if(!!ret.reason){
                    $('#failedMessage').text(ret.reason);
                } else {
                    $('#failedMessage').text("Unknown server error.");
                }
            }

        });
    });
});