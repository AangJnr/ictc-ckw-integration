package applab.client.search.utils;

import applab.client.search.model.MeetingActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skwakwa on 9/2/15.
 */
public class  AgentVisitUtil {

    public static String TV_PROGRAM="" +
            "GTV\nEvery Sunday\n" +
            "3:00pm - 3:30pm\n" +
            "Radio \n" +
            "Volta Region (Twi-Ewe)" +
            "\nSun 7pm-9pm\n" +
            "Brong Ahafo 7 to 8 \n" +
            "Volta - Twi, Ewe (Volta Star Radio) \n" +
            "Brong Ahafo Radio B in Twi" +
            "\nMore Information Call: 057665186";
    public static String LAUNCH_CKW="" +
            "LAUNCH CKW" +
            "\nFollow Up with the crop and drill down " ;
    public static String LAUNCH_TAROWORKS="" +
            "Launch Tarowork \n" +
            "Select Job \n" ;

    public static String ATTENDANCE="" +
            "CLICK ON Meetings" +
            "\nAdd Meetings and take Attendance " ;
    public static String MEETING_QUESITONS="" +
            "CLICK ON Meetings" +
            "\nAdd Meetings and take Attendance " ;
    public static List<MeetingActivity> getMeetingActivity(int meetingIndex){
        List<MeetingActivity> activities = new ArrayList<MeetingActivity>();
        if(1==meetingIndex){
            activities.add(new applab.client.search.model.MeetingActivity("Get Ploughing Dates for Tractor","G","A",1,""));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Bio Data", "I", "T",2,LAUNCH_TAROWORKS+"2. FARMER REGISTRATION"));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Farmer Data","I","T",3,LAUNCH_TAROWORKS+"2. FARMER REGISTRATION"));
            activities.add(new applab.client.search.model.MeetingActivity("Share input package","-","A",4,"Share Inputs"));
            activities.add(new applab.client.search.model.MeetingActivity("Share TV Schedule","A","A",5,TV_PROGRAM));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Request for Assistance","G","c",6,LAUNCH_CKW));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Meeting Question","G","A",7,MEETING_QUESITONS));
            activities.add(new applab.client.search.model.MeetingActivity("Take Attendance","G","A",8,ATTENDANCE));
        }else if(2==meetingIndex){
            activities.add(new applab.client.search.model.MeetingActivity("Collect farm Measurement Data","G","A",1,""));
            activities.add(new applab.client.search.model.MeetingActivity("Baseline Data Collection", "I", "T",2,LAUNCH_TAROWORKS+"3. Farmer Baseline Data"));
            activities.add(new applab.client.search.model.MeetingActivity("Farm Planning","I","T",3,LAUNCH_TAROWORKS+"5. Farm Management Plan"));
            activities.add(new applab.client.search.model.MeetingActivity("Deliver Inputs","-","A",4,"To be Decided"));
            activities.add(new applab.client.search.model.MeetingActivity("Confirm Delivery of Inputs/Receipt","A","A",5,"To be Decided"));
            activities.add(new applab.client.search.model.MeetingActivity("Provide technical Assistance","G","C",6,LAUNCH_CKW));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Request for Attendance","G","A",7,ATTENDANCE));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Meeting Question","G","A",8,MEETING_QUESITONS));
            activities.add(new applab.client.search.model.MeetingActivity("Share TV Schedule","G","A",9,TV_PROGRAM));
        }else if(3==meetingIndex){
            activities.add(new applab.client.search.model.MeetingActivity("Confirm Delivery of Inputs/Receipt","A","A",1,"to BE DECIDED"));
            activities.add(new applab.client.search.model.MeetingActivity("Confirm Activities Agreed on Previous visit", "I", "A",2,"to decided"));
            activities.add(new applab.client.search.model.MeetingActivity("Provide technical Assistance","G","C",3,LAUNCH_CKW));
            activities.add(new applab.client.search.model.MeetingActivity("Play A Game","-","A",4,"Game Coming Soon"));
            activities.add(new applab.client.search.model.MeetingActivity("Share TV Schedule","G","A",5,TV_PROGRAM));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Request for ASSISTANCE","G","C",6,LAUNCH_CKW));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Meeting Question","G","A",7,MEETING_QUESITONS));
            activities.add(new applab.client.search.model.MeetingActivity("Take Attendance","G","A",8,ATTENDANCE));
        }else if(4==meetingIndex){
            activities.add(new applab.client.search.model.MeetingActivity("Farm Plan Update","G","T",1,LAUNCH_TAROWORKS+" 5. Farm Management Plan"));
            activities.add(new applab.client.search.model.MeetingActivity("Crop Inspection and update of Crop Status", "I", "T",2,LAUNCH_TAROWORKS+"5. Farm Management Plan"));
            activities.add(new applab.client.search.model.MeetingActivity("Provide technical Assistance","G","C",3,LAUNCH_CKW));
            activities.add(new applab.client.search.model.MeetingActivity("Baseline on Post Harvest Management","I","T",4,LAUNCH_TAROWORKS));
            activities.add(new applab.client.search.model.MeetingActivity("Post harvest management Planning","-","T",5,LAUNCH_TAROWORKS));
            activities.add(new applab.client.search.model.MeetingActivity("Provide technical Assistance on Post harvest Management","G","C",6,LAUNCH_CKW));
            activities.add(new applab.client.search.model.MeetingActivity("Agree Activities to be completed by Next Level","G","A",7,"to"));
            activities.add(new applab.client.search.model.MeetingActivity("Share TV Schedule","G","A",8,TV_PROGRAM));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Meeting Question","G","A",9,MEETING_QUESITONS));
            activities.add(new applab.client.search.model.MeetingActivity("Take Attendance","G","A",10,ATTENDANCE));

        }else if(5==meetingIndex){
            activities.add(new applab.client.search.model.MeetingActivity("Confirm Activity Agreed on Preview Visit","G","A",1,""));

            activities.add(new applab.client.search.model.MeetingActivity("Provide technical Assistance on Post Harvest","G","C",6,LAUNCH_CKW));
            activities.add(new applab.client.search.model.MeetingActivity("Discuss Food Security", "I", "C",2,LAUNCH_CKW));
            activities.add(new applab.client.search.model.MeetingActivity("Play Game","I","A",3,"Coming Soon"));
            activities.add(new applab.client.search.model.MeetingActivity("Farm Plan Update","-","T",4,LAUNCH_TAROWORKS));
            activities.add(new applab.client.search.model.MeetingActivity("Agree Activities to be completed on next visit","A","A",5,"Things agreed on"));

            activities.add(new applab.client.search.model.MeetingActivity("Share TV Schedule","G","A",9,TV_PROGRAM));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Request for Assistance","G","C",7,LAUNCH_CKW));
            activities.add(new applab.client.search.model.MeetingActivity("Take Attendance","G","A",8,ATTENDANCE));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Meeting Questions","G","A",8,MEETING_QUESITONS));
        }else if(6==meetingIndex){
            activities.add(new applab.client.search.model.MeetingActivity("Provide Results","G","A",1,"Results"));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Payment", "I", "A",2,"Collect Payments"));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Farmer Feedback","I","A",3,"Collect Farmer Feedback>"));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Agent Feedback","-","A",4,"Collect Agent Feedback"));
        }
        return  activities;
    }
}