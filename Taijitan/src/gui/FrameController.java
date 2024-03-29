/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.Activity;
import domain.Domaincontroller;
import domain.User;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class FrameController extends HBox {
    private Domaincontroller dc;
    private NavController nav;
    private WelcomeController welcome;
    private MembersController members;
    private OverviewsController overviews;
    private ActivitiesController activities;
    private ScoresController scorebord;
    private CourseMaterialController courseMaterial;
    private Object current;
    Rectangle2D bounds;
    private _OverviewPresentsController pc;
    private _OverViewRegisteredUsersController oru;
    private _OverviewActivitiesController oa;
    private _OverviewScorebordController osc;
    private ListPanelController listpanel;
    private Stage stage;
    private String afkomstig;

    public FrameController(Domaincontroller dc) {
        this.dc = dc;
        this.pc = new _OverviewPresentsController(dc, this);
        this.oru = new _OverViewRegisteredUsersController(dc,this);
        this.oa = new _OverviewActivitiesController(dc, this);
        this.osc = new _OverviewScorebordController(dc, this);
        this.listpanel = new ListPanelController(dc, this);
        setupStart();
    }

    private void setupStart() {
        nav = new NavController(this);
        Screen screen = Screen.getPrimary();
        bounds = screen.getVisualBounds();
        setWidth(bounds.getWidth());
        setHeight(bounds.getHeight());
        getChildren().add(nav);
        setupWelcome();
    }

    private void setupWelcome() {
        welcome = new WelcomeController();
        current = welcome;
        welcome.setMinWidth(bounds.getWidth() - nav.getPrefWidth());
        getChildren().add(welcome);
    }

    private void setupMember() {
        this.listpanel = new ListPanelController(dc, this);
        this.setVisibleAdd(true);
        this.dc.setCurrentUser(null);
        members = new MembersController(dc, this);
        this.dc.addPropertyChangeListenerCurrentUser(members);
        current = members;
        members.setMinWidth(bounds.getWidth() - nav.getPrefWidth()-listpanel.getPrefWidth());
        listpanel.fillWithMembers();
        getChildren().addAll(listpanel,members);
    }

    private void setupOverviews() {
        overviews = new OverviewsController(dc, this, pc, oru, oa, osc);
        current = overviews;
        getChildren().add(overviews);
    }

    public void setupActivities(){
        this.listpanel = new ListPanelController(dc, this);
        this.setVisibleAdd(true);
        activities = new ActivitiesController(dc,this);
        this.dc.addPropertyChangeListenerCurrentActivity(activities);
        current = activities;
        listpanel.fillWithActivities();
        getChildren().addAll(listpanel,activities);
    }
    public void setupCourseMaterial(){
        this.listpanel = new ListPanelController(dc,this);
        this.setVisibleAdd(true);
        courseMaterial = new CourseMaterialController(dc,this);
        this.dc.addPropertyChangeListenerCurrentCourseMaterial(courseMaterial);
        current = courseMaterial;
        listpanel.fillWithCourseMaterial();
        getChildren().addAll(listpanel,courseMaterial);
    }

    public void setupScorebord(){
        this.listpanel = new ListPanelController(dc, this);
        this.setVisibleAdd(true);
        this.dc.setCurrentUser(null);
        scorebord = new ScoresController(dc, this);
        this.dc.addPropertyChangeListenerCurrentUserScore(scorebord);
        current = scorebord;
        listpanel.fillWithMembersScore();
        getChildren().addAll(listpanel,scorebord);
    }

    public void changeContent(String string) {
        getChildren().remove(current);
        getChildren().remove(listpanel);

        switch (string.toLowerCase()) {
            case "members":
                setupMember();
                break;
            case "welcome":
                setupWelcome();
                break;
            case "overviews":
                setupOverviews();
                break;
            case "activities":
                setupActivities();
                break;
            case "scorebord":
                setupScorebord();
                break;
            case "coursematerial":
                setupCourseMaterial();
                break;
        }
    }

    public void changeToMembersWithSelectedUser(User user, String afkomstig){
        clearNodes();
        members = new MembersController(dc, this);
        current = members;
        this.afkomstig = afkomstig;
        members.setMinWidth(bounds.getWidth() - nav.getPrefWidth());
        members.fillFieldsWithSelectedUser(user);
        getChildren().add(members);
    }

    public void changeToActivityWithSelectedUser(Activity selectedActivity, String afkomstig) {
        clearNodes();
        activities = new ActivitiesController(dc, this);
        current = activities;
        this.afkomstig = afkomstig;
        activities.setMinWidth(bounds.getWidth() - nav.getPrefWidth());
        activities.fillFieldWithSelectedActivity(selectedActivity);
        getChildren().add(activities);
    }

    public void clearNodes(){
        this.getChildren().remove(pc);
        this.getChildren().remove(oru);
        this.getChildren().remove(overviews);
        this.getChildren().remove(scorebord);
    }

    public void terug(){
        getChildren().remove(current);
        getChildren().remove(listpanel);
        setupOverviews();

        switch (afkomstig){
            case "activitiesOverview":
                this.afkomstig = "";
                overviews.refreshActivities();
                overviews.showActivities();
                break;
            case "presentOverview":
                this.afkomstig = "";
                overviews.refreshPresents();
                overviews.showPresents();
                break;
            case "registeredOverview":
                this.afkomstig = "";
                overviews.refreshRegistered();
                overviews.showRegistrations();
                break;
            case "scorebordOverview":
                this.afkomstig = "";
                overviews.refreshChampionship();
                overviews.showChampionship();
                break;
        }
    }

    public void updateListPanelMembers(){
        listpanel.fillWithMembers();
    }
    public void updateListPanelActivities(){ listpanel.fillWithActivities();}
    public void updateListPanelCourseMaterial() {
        changeContent("coursematerial");
    }

    public boolean isAddingMember(){return members.getIsAdd();}
    public void setDisableAdd(boolean b){listpanel.setDisableAdd(b);}
    public void setVisibleAdd(boolean b){listpanel.setVisibleAdd(b);}

    public void setIsAddMembers(boolean b){members.setIsAdd(b);}
    public void setBtnEditTextMembers(String s)
    {
        members.setBtnEditText(s);
    }
    public void setBtnDeleteTextMembers(String s)
    {
        members.setBtnDeleteText(s);
    }
    public void setDisableDeleteMembers(boolean b)
    {
        members.setDisableDelete(b);
    }
    public void enableFieldsMember() {members.enableFieldsMember();};
    public void emptyFieldsMember() {members.emptyFieldsMember();}


    public void emptyFieldsActivities(){activities.emptyFields();}
    public void enableFieldsActivities(){activities.enableFields();}
    public void setIsAddActivities(boolean b){activities.setIsAdd(b);}
    public void setBtnEditTextActivities(String s)
    {
        activities.setBtnEditText(s);
    }
    public void setBtnDeleteTextActivities(String s)
    {
        activities.setBtnDeleteText(s);
    }
    public void setDisableDeleteActivities(boolean b)
    {
        activities.setDisableDelete(b);
    }
    public boolean isAddingActivity(){return activities.getIsAdd();}

    public void emptyFieldsCourseMaterial(){courseMaterial.emptyFields();}
    public void enableFieldsCourseMaterial(){courseMaterial.enableFields();}
    public void setIsAddCourseMaterial(boolean b){courseMaterial.setIsAdd(b);}
    public void setBtnEditTextCourseMaterial(String s)
    {
        courseMaterial.setBtnEditText(s);
    }
    public void setBtnDeleteTextCourseMaterial(String s)
    {
        courseMaterial.setBtnDeleteText(s);
    }
    public void setDisableDeleteCourseMaterial(boolean b)
    {
        courseMaterial.setDisableDelete(b);
    }


    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    public String getAfkomstig() {
        return afkomstig;
    }

    public Stage getStage()
    {
        return this.stage;
    }
}
