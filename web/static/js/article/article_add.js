

$(function () {
    var dateNow = new Date().toISOString();
    $('#postTime').val(dateNow.substring(0,dateNow.length-1));

    $('#btnSubmit').click(function(){
        var data={};
        data.title = $('#title').val();
        data.content = $('#content').val();
        var postTime = $('#postTime').val();
        if ((postTime.match(/:/g) || []).length === 1) {
            postTime += ":00";
        }
        postTime = postTime.replace('T', ' ');
        data.postTime = postTime;
        data.isPrivate = $('#isPrivate').is(':checked');
        console.log(data);
        $.ajax({
            url:'api/articleAdd',
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
