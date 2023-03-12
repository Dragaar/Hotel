type="text/javascript"
    $(document).ready(
        function() {
            $('.numeric').each(function(i) {
                var number = i + 1;
                $(this).find('td:first').text(number);
            });
        }
    );
