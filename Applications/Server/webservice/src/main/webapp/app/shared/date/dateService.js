angular.module('collaboApp').factory('DateService', function() {
     var date = new Date();
    var month = date.getMonth()+ 1;
    month = Array(Math.max(2 - String(month).length + 1, 0)).join(0) + month;
    var day = date.getDate();
    day = Array(Math.max(2 - String(day).length + 1, 0)).join(0) + day;
    var dateString = date.getFullYear() + "-" + month + "-" + day
    + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
    return {
        date : dateString
    };
});