





$(function () {
    var searchParams = new URLSearchParams(window.location.search)
    var aid = searchParams.get('aid')

    $('#btnModify').click(function(){

    });

    $('#btnDelete').click(function(){
        $.ajax({
            url: '/articleDelete',
            data: {'aid': aid},
            method: 'post',
            success: function(){

            },
            error: function(){

            }

        })
    });
});
