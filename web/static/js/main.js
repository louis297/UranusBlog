var lock = true;
var currentBlogList = 'all';
var ARTICLES_PER_PAGE = 10;

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
            isLogged();
        },
        error: function(){
            isLogged();
        }
    })
}

function updateLoginForm(success, ret){
    if(success){
        // display username and logout button
        $('#loginForm').html('<button class="btn btn-outline-success my-2 my-sm-1" type="button" id="manageButton">Account Manage</button>\n' +
            '            <!-- popup modal for register -->\n' +
            '            <button class="btn btn-outline-info my-2 my-sm-1" type="button" id="logoutButton" onclick="logoutButtonClicked();">Logout</button>')

        // display the "my blog" tag
        $('#ownBLog').show();
    } else {
        $('#ownBLog').hide();
        // display login form
        $('#loginForm').html('<input class="form-control mr-sm-1" type="text" name="Username" placeholder="Username" aria-label="Username" id="Username">\n' +
            '            <input class="form-control mr-sm-1" type="text" name="Password" placeholder="Password" aria-label="Password" id="Password">\n' +
            '            <!--in main.js add ajax call for login-->\n' +
            '            <button class="btn btn-outline-success my-2 my-sm-1" type="button" id="loginButton" onclick="loginButtonClicked()">Login</button>\n' +
            '            <!-- popup modal for register -->\n' +
            '            <button class="btn btn-outline-info my-2 my-sm-1" type="button" id="registerButton" data-toggle="modal" data-target="#registerModal">Register</button>');
    }
}
function isLogged(){
    $.ajax({
        url: '/isLogged',
        method: 'get',
        dataType: 'json',
        success: function(ret){
            console.log(ret);
            if(ret.is_logged === true){
                console.log("login success")
                updateLoginForm(true, ret);
            } else {
                updateLoginForm(false, ret);
            }
        },
        error: function(ret){
            console.log(ret)
            updateLoginForm(false, ret);
        }
    });
}

function loginButtonClicked(){
    loginAction();
}

function logoutButtonClicked(){
    // call /logout
    $.ajax({
        url: '/logout',
        method: 'get',
        success: function(){
            updateLoginForm(false, null);
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

    isLogged();

    // var loginBtn = $('#loginButton');
    // loginBtn.click(loginAction);

    var searchParams = new URLSearchParams(window.location.search)
    if (searchParams.has('own')){
        var own =searchParams.get('own')
    } else {
        var own = false;
    }
    console.log(own)
    getArticleList(own)
})