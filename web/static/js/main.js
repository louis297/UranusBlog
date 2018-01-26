var lock = true;
var currentBlogList = 'all';
var ARTICLES_PER_PAGE = 10;
var isLogged = false;

function loginAction(){
    var username = $('#Username').val();
    var password = $('#Password').val();
    if(!username){
        alert("Please input username.");
        return;
    } else if (!password) {
        alert("Please input password.");
        return;
    }
    var dataStr = 'Username=' + username +'&Password=' + password;
    $.ajax({
        url: '/login',
        method: 'post',
        data: dataStr,
        success: function(ret) {
            console.log(ret);
        }
    })
}

$(function () {
    $('#allBlogBtn').click(function(){

        console.log('all clicked')
        if(!lock && currentBlogList !=='all') {
            lock = true;
            $('#ownBlog').removeClass('active');
            $('#allBlog').addClass('active');
            currentBlogList = 'all';
            getArticleList('false')
        }
    });
    $('#ownBlogBtn').click(function(){

        console.log('own clicked')
        if(!lock && currentBlogList !=='own') {
            lock = true;
            $('#allBlog').removeClass('active');
            $('#ownBlog').addClass('active');
            currentBlogList = 'own';
            getArticleList('true')
        }
    });


    var loginBtn = $('#loginButton');
    loginBtn.click(loginAction);

    var searchParams = new URLSearchParams(window.location.search)
    if (searchParams.has('own')){
        var own =searchParams.get('own')
    } else {
        var own = false;
    }
    console.log(own)
    getArticleList(own)
})