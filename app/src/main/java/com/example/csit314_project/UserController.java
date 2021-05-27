/*
filename    UserController.java
authors     Zheng Qingping
UOW email   qzheng011@uowmail.edu.au
Course:     CSIT314
Brief Description:
UserController singleton managing persistent information in app lifetime
*/
package com.example.csit314_project;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserController {

    private static UserController INSTANCE = null;

    private UserController() {}

    //just a temporary holder for the user
    public User currentUser;

    /*
    Function Name: getInstance
    Brief Description: Returns the static Instance of the UserController
    Parameters: None
    */
    public static UserController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserController();
        }
        return(INSTANCE);
    }

    /*
    Function Name: validateOnLogin
    Brief Description: verifies the entry from the calling boundary and the User class
    Parameters:
    username : string received from the boundary
    password : string received from the boundary
    context : app context for the database opening
    */
    protected boolean validateOnLogin(String username, String password, Context context) {
        User user = new User();
        user = user.findSingleUserByUsername(username, context);
        //if there is such a user
        if (user != null) {
            //double check password
            if (user.password.equals(password) && !user.isSuspend) {
                this.currentUser = user;
                return true;
            }
            return false;
        }
        return false;
    }

    /*
    Function Name: validateOnAddUser
    Brief Description: tests the database if a user can be added
    Parameters:
    All parameters from the User class
    context : app context for the database opening
    */
    protected boolean validateOnAddUser(String NRIC, String gender, String firstName, String lastName, String email, String contactNumber, String username, String password, String userType, Context context) {
        User user = new User();
        //if the user cannot be found
        if (user.findSingleUserByNRIC(NRIC, context) == null) {
            user.addUser(NRIC, gender, firstName, lastName, email, contactNumber, username, password, userType, context);
            return true;
        }
        return false;
    }

    /*
    Function Name: validateOnSearchUser
    Brief Description: returns List of users based on parameters from boundary
    Parameters:
    nric : string received from the boundary
    userType : string received from the boundary
    username : string received from the boundary
    context : app context for the database opening
    */
    public List<User> validateOnSearchUser(String nric, String userType, String nameContains, Context context) {
        User user = new User();
        //if the user cannot be found
        return user.findUserSpecial(nric, userType, nameContains, context);
    }

    /*
    Function Name: validateOnSearchUser
    Brief Description: overload, returns single user based on parameters from boundary
    Parameters:
    nric : string received from the boundary
    context : app context for the database opening
    */
    public User validateOnSearchUser(String nric, Context context) {
        User user = new User();
        //if the user cannot be found
        return user.findSingleUserByNRIC(nric, context);
    }

    /*
    Function Name: toggleSuspension
    Brief Description: Causes single user to flip suspension
    Parameters:
    nric : string received from the boundary
    context : app context for the database opening
    */
    public void toggleSuspension(String nric, Context context) {
        User user = new User();
        user = user.findSingleUserByNRIC(nric, context);
        //if the user cannot be found
        if (user != null) {
            user.setSuspend(!user.isSuspend, nric, context);
        }
    }

    /*
    Function Name: toggleCovid
    Brief Description: Causes single user to flip suspension
    Parameters:
    nric : string received from the boundary
    context : app context for the database opening
    */
    public void toggleCovid(String nric, Context context) {
        User user = new User();
        user = user.findSingleUserByNRIC(nric, context);
        //if the user cannot be found
        if (user != null) {
            user.setCovid(!user.hasCovid, nric, context);
        }
    }

    /*
    Function Name: validateUpdateVaccineBrand
    Brief Description: changes brand to selected brand
    Parameters:
    nric : string received from the boundary
    brand : string received from the boundary
    context : app context for the database opening
    */
    public void validateUpdateVaccineBrand(String NRIC, String selectedBrand, Context context) {

        Vaccination vaccination = new Vaccination();
        vaccination = vaccination.findVaccinationByNRIC(NRIC, context);

        if (vaccination != null) {
            vaccination.setBrand(selectedBrand, NRIC, context);
        }
    }

    public void validateUpdateFirstVaccineDate(String NRIC, String firstVaccine, Context context) {

        Vaccination vaccination = new Vaccination();
        vaccination = vaccination.findVaccinationByNRIC(NRIC, context);
        String[] splitDate = firstVaccine.split("/");
        if (splitDate[0].length() < 2)
        {
            splitDate[0] = "0" + splitDate[0];
        }
        if (splitDate[1].length() < 2)
        {
            splitDate[1] = "0" + splitDate[1];
        }
        String fixedFirstVaccine = splitDate[0] + "/" + splitDate[1]+ "/" + splitDate[2];
        if (vaccination != null) {
            vaccination.setFirstVaccine(fixedFirstVaccine, NRIC, context);
        }
    }

    public void validateUpdateSecondVaccineDate(String NRIC, String secondVaccine, Context context) {

        Vaccination vaccination = new Vaccination();
        vaccination = vaccination.findVaccinationByNRIC(NRIC, context);
        String[] splitDate = secondVaccine.split("/");
        if (splitDate[0].length() < 2)
        {
            splitDate[0] = "0" + splitDate[0];
        }
        if (splitDate[1].length() < 2)
        {
            splitDate[1] = "0" + splitDate[1];
        }
        String fixedSecondVaccine = splitDate[0] + "/" + splitDate[1]+ "/" + splitDate[2];
        if (vaccination != null) {
            vaccination.setSecondVaccine(fixedSecondVaccine, NRIC, context);
        }
    }

    public boolean validateOnAddTravelHistory(String nric, String timeIn, String timeOut, String location, Context context) {
        TravelHistory travelHistory = new TravelHistory();
        return travelHistory.addTravelHistory(nric, timeIn, timeOut, location, context);
    }

    public boolean validateOnAcknowledgeAlert(String nric, String dateTime, String message, Context context) {
        Alert alert = new Alert();
        return alert.acknowledgeAlert(nric, dateTime, message, context);
    }

    /*
    Function Name: validateOnAddUser
    Brief Description: tests the database if a user can be added
    Parameters:
    All parameters from the User class
    context : app context for the database opening
    */
    protected boolean validateOnAddAlert(String NRIC, String message, Context context) {
        Alert alert = new Alert();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
        Date date = new Date();
        String currDate = formatter.format(date);
        return alert.addAlert(NRIC, currDate, message, context);
    }

    public List<TravelHistory> validateOnSearchDate(String NRIC, String date, Context context)
    {
        Employment employment = new Employment();
        String location = employment.findLocationByNRIC(NRIC, context);

        TravelHistory travelHistory = new  TravelHistory();
        String[] splitDate = date.split("/");
        if (splitDate[0].length() < 2)
        {
            splitDate[0] = "0" + splitDate[0];
        }
        if (splitDate[1].length() < 2)
        {
            splitDate[1] = "0" + splitDate[1];
        }
        String fixedDate = splitDate[0] + "/" + splitDate[1]+ "/" + splitDate[2];

        return travelHistory.findCustomersByDateAndLocation(location, fixedDate, context);
    }

    public String validateOnRetrieveEmploymentLocation(String NRIC, Context context) {
        Employment employment = new Employment();
        return employment.findLocationByNRIC(NRIC, context);
    }

    public List<User> validateOnRetrieveColleagues(String location, Context context){
        Employment employment = new Employment();
        List<Employment> colleagues = employment.gatherEmployment(location, context);
        User user = new User();
        return user.findUsersByEmployment(colleagues, context);

    }

    public List<String> validateGetByMostCheckIn(int limit, Context context) {
        TravelHistory travelHistory = new TravelHistory();
        return travelHistory.getByMostCheckIn(limit, context);
    }

    public List<String> validateGetByMostCases(int limit, Context context) {
        TravelHistory travelHistory = new TravelHistory();
        return travelHistory.getByMostCases(limit, context);
    }
}
