package stephen_wallace.software_engineering.assessment;

public class Validation {


    /*
     *   Validates the message header
     *
     *   @params  header  message header
     */
    public boolean validateHeader(String header) {
        return header.matches("([ETS]{1}[0-9]{9})");
    }

    /*
     *   Validates the date
     *
     *   @params  data  a date string
     */
    public boolean validateDate(String date){
        //Regex from https://regexlib.com/REDetails.aspx?regexp_id=320
        return date.matches("((((0?[1-9]|[12]\\d|3[01])[\\.\\-\\/](0?[13578]|1[02])[\\.\\-\\/]((1[6-9]|[2-9]\\d)?\\d{2}))|((0?[1-9]|[12]\\d|30)[\\.\\-\\/](0?[13456789]|1[012])[\\.\\-\\/]((1[6-9]|[2-9]\\d)?\\d{2}))|((0?[1-9]|1\\d|2[0-8])[\\.\\-\\/]0?2[\\.\\-\\/]((1[6-9]|[2-9]\\d)?\\d{2}))|(29[\\.\\-\\/]0?2[\\.\\-\\/]((1[6-9]|[2-9]\\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00)))|(((0[1-9]|[12]\\d|3[01])(0[13578]|1[02])((1[6-9]|[2-9]\\d)?\\d{2}))|((0[1-9]|[12]\\d|30)(0[13456789]|1[012])((1[6-9]|[2-9]\\d)?\\d{2}))|((0[1-9]|1\\d|2[0-8])02((1[6-9]|[2-9]\\d)?\\d{2}))|(2902((1[6-9]|[2-9]\\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00))))");
    }

    /*
     *   Validates the url
     *
     *   @params  url  contains a url string
     */
    public boolean validateURL(String url) {
        //Regex from https://stackoverflow.com/questions/3809401/what-is-a-good-regular-expression-to-match-a-url
        return url.matches("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)");
    }

    /*
     *   Validates the sort code
     *
     *   @params  sortCode  contains a sortCode
     */
    public boolean validateSortCode(String sortCode) {
        //Regex from https://stackoverflow.com/questions/11341957/uk-bank-sort-code-javascript-regular-expression
        return sortCode.matches("(?!(?:0{6}|00-00-00))(?:\\d{6}|\\d\\d-\\d\\d-\\d\\d)");
    }

    /*
     *   Validates the SIR message body
     *
     *   @params  body  message body
     */
    public boolean validateNatureOfIncident(String body){
        String[] naturesOfIncident = new String[] {"Theft", "Staff Attack", "ATM Theft", "Raid", "Customer Attack", "Staff Abuse", "Bomb Threat", "Terrorism", "Suspicious Incident", "Intelligence", "Cash Loss"};

        for (String nature : naturesOfIncident){
            if (body.contains(nature)) {
                return true;
            }
        }
        return false;
    }

    /*
     *   Validates an international phone number
     *
     *   @params  number  contains a phone number
     */
    public boolean validatePhoneNum(String number) {
        //Regex from https://www.oreilly.com/library/view/regular-expressions-cookbook/9781449327453/ch04s03.html
        return number.matches("\\+(?:[0-9]●?){6,14}[0-9]");
    }

    /*
     *   Validates an email address
     *
     *   @params  email  contains an email address
     */
    public boolean validateEmail(String email) {
        //Regex from https://stackoverflow.com/questions/201323/how-to-validate-an-email-address-using-a-regular-expression
        return email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    }

    /*
     *   Validates a twitter name
     *
     *   @params  twitter  contains a twitter username
     */
    public boolean validateTwitterName(String twitter) {
        //Regex from https://stackoverflow.com/questions/8650007/regular-expression-for-twitter-username
        return twitter.matches("[@]{1}(\\w){1,15}");
    }
}
