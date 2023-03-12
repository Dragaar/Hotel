type="JQuery"
$(document).ready(function(){

    let lang = $('#datepickerScript').attr("data-pickerLang");
    let currentDate = $('#datepickerScript').attr("data-currentDate");
    let disabledDatesListExist = $('#datepickerScript').attr("data-disabledDatesListExist");
    let disabledDatesList = $('#datepickerScript').attr("data-disabledDatesList");

    if(disabledDatesListExist == "true"){
        $('.datepicker').datepicker({
            "format": "yyyy-mm-dd",
            "language": lang,
            "startDate": currentDate,
            "datesDisabled": disabledDatesList.toString()
        });
    } else {
        $('.datepicker').datepicker({
            "format": "yyyy-mm-dd",
            "language": lang,
            "startDate": currentDate,
        });
    }

});