package stephen_wallace.software_engineering.assessment;

import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProcessMessages {
    private final ReadAbbreviations readAbbreviations = new ReadAbbreviations();
    private final List<String> hashtags = new ArrayList<>();
    private final List<String> mentions = new ArrayList<>();
    private final List<String> trending = new ArrayList<>();
    private final List<String> abbreviations = readAbbreviations.getAbbreviations();
    private final List<String> readings = readAbbreviations.getReadings();
    private final List<String> urls = new ArrayList<>();
    private final List<String> SIRs = new ArrayList<>();
    private final Validation validator = new Validation();

    /*
     *   Detects the type of message input into the program
     *
     *   @param  header  String containing the header
     */
    public MessageType detectMessageType(String header){
        MessageType detectedType = null;
        if (validator.validateHeader(header)){
            if (header.charAt(0) == 'S'){
                detectedType = MessageType.SMS;
            } else if (header.charAt(0) == 'E'){
                detectedType = MessageType.EMAIL;
            } else if (header.charAt(0) == 'T') {
                detectedType = MessageType.TWITTER;
            }
        } else {
            detectedType = MessageType.ERROR;
            Alert messageError = new Alert(Alert.AlertType.ERROR);
            messageError.setTitle("Error");
            messageError.setHeaderText("Input Error");
            messageError.setContentText("Please check the header input is correct");
            messageError.show();
        }

        return detectedType;
    }

    /*
     *   Validates and processes twitter messages
     *
     *   @param  body  String containing the message body
     */
    public String processTwitterMessage(String body) {
        String[] wordsInBody = body.split(" |\n");
        List<String> processedWords = new ArrayList<>();

        if (validator.validateTwitterName(wordsInBody[0]) && (body.length() <= wordsInBody[0].length() + 1 + 140)){
            for (String word: wordsInBody) {
                if (word.charAt(0) == '#'){
                    hashtags.add(word);
                } else if (word.charAt(0) == '@' && !word.equals(wordsInBody[0])){
                    mentions.add(word);
                }
                word = checkAbbreviation(word);
                processedWords.add(word);
            }

            return String.join(" ", processedWords);
        }else {
            return "Error: Invalid Twitter Message";
        }
    }

    /*
     *   Validates and processes SMS messages
     *
     *   @param  body  String containing the message body
     */
    public String processSMSMessage(String body){
        String[] wordsInBody = body.split(" |\n");
        List<String> processedWords = new ArrayList<>();

        if (validator.validatePhoneNum(wordsInBody[0]) && (body.length() <= (wordsInBody[0].length()+1+140))){
            for (String word: wordsInBody){
                word = checkAbbreviation(word);
                processedWords.add(word);
            }
            return String.join(" ", processedWords);
        }

        return "Error: Invalid SMS Message";
    }

    /*
     *   Detects the type of email being processed
     *
     *   @param  body  String containing the message body
     */
    public String processEmail(String body) {
        String[] wordsInBody = body.split(" |\n");
        String processedEmail = null;

        if (validator.validateEmail(wordsInBody[0]) && (body.length() <= wordsInBody[0].length() + 20 + 1028)){
            if (wordsInBody[1].equals("SIR") && validator.validateDate(wordsInBody[2])){
                processedEmail = processSIREmail(body);
            } else {
                processedEmail = processEmailMessage(body);
            }
            return processedEmail;
        }

        return "Error: Invalid Email";
    }

    /*
     *   Validates and processes a standard email message
     *
     *   @param  body  String containing the message body
     */
    private String processEmailMessage(String body) {
        String[] wordsInBody = body.split(" |\n");
        List<String> processedWords = new ArrayList<>();

        for (String word : wordsInBody){
            if (validator.validateURL(word)){
                urls.add(word);
                processedWords.add("<URL Quarantined>");
            } else {
                processedWords.add(word);
            }
        }

        return String.join(" ", processedWords);
    }

    /*
     *   Validates and processes a significant incident report email
     *
     *   @param  body  String containing the message body
     */
    public String processSIREmail(String body){
        String[] wordsInBody = body.split(" |\n");
        List<String> processedWords = new ArrayList<>();
        String sortCode = null;

        for (String word : wordsInBody){
            if (validator.validateURL(word)){
                urls.add(word);
                processedWords.add("<URL Quarantined>");
            } else {
                processedWords.add(word);
            }
            if (validator.validateSortCode(word)) {
                sortCode = word;
                addSIRToList(sortCode,body);
            }
        }

        return String.join(" ", processedWords);
    }

    /*
     *   Creates a trending list from the twitter messages
     */
    private void createTrendingList(){
        List<String> added = new ArrayList<>();
        for (String hashtag: hashtags){
            if (!added.contains(hashtag)){
                trending.add(hashtag+":"+ Collections.frequency(hashtags, hashtag));
                added.add(hashtag);
            }
        }
    }

    /*
     *   Checks if a word is an abbreviation
     *
     *   @param  word  String containing a word to be checked
     */
    private String checkAbbreviation(String word) {
        if (abbreviations.contains(word)){
            word = word + " <" + readings.get(abbreviations.indexOf(word)) + ">";
        }
        return word;
    }

    /*
     *   Adds a significant incident report to a list
     *
     *   @param  sortCode  String containing the sort code of a branch
     *   @param  body  String containing the message body
     */
    private void addSIRToList(String sortCode, String body) {
        if (validator.validateNatureOfIncident(body)){
            String[] naturesOfIncident = new String[] {"Staff Attack", "ATM Theft", "Raid", "Customer Attack", "Staff Abuse", "Bomb Threat", "Terrorism", "Suspicious Incident", "Intelligence", "Cash Loss", "Theft"};

            for (String nature : naturesOfIncident) {
                if (body.contains(nature)){
                    SIRs.add("SortCode: " + sortCode + ", Nature of Incident: " + nature);
                    break;
                }
            }
        }
    }

    /*
     *   Returns the trending list as a String
     */
    public String getTrending(){
        createTrendingList();
        
        return String.join(" ", trending);
    }

    /*
     *   Returns the mentions list as a String
     */
    public String getMentions(){
        return String.join("\n", mentions);
    }

    /*
     *   Returns the SIR list as a String
     */
    public String getSIRs() {
        return String.join("\n", SIRs);
    }
}
