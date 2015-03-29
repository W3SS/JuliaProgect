

function MessageUtil(){

    var showMessage = function(text, type){
        var message = $('<div class="alert '+ type + ' message" style="display: none;">'+text+'</div>');
        message.appendTo($('body')).fadeIn(100).delay(2000).fadeOut(300);
    };
    var alertDanger ="alert-danger";
    var alertInfo ="alert-info";
    return {
        showErrorMessage: function (text){
            showMessage(text, alertDanger);
        },

        showInfoMessage: function (text){
            showMessage(text, alertInfo) ;
        }

    };

}

