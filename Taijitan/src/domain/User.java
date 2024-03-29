/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.ScoreDTO;
import dto.UserDTO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.stream.Collector;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "User.deleteActivities", query = "DELETE FROM ActivityMember am WHERE am.memberId = :id"),
        @NamedQuery(name = "User.deleteSessions", query = "DELETE from SessionMember sm where sm.memberId = :id")
})
public class User extends RecursiveTreeObject<User> implements Serializable {

    //region DBProperties
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "UserId")
    private Integer userId;

    @Column(name = "Name")
    private String name;

    @Column(name = "FirstName")
    private String firstName;

    @Basic(optional = false)
    @Column(name = "DateOfBirth")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBirth;

    @Column(name = "Street")
    private String street;

    @Basic(optional = false)
    @Column(name = "Country")
    private int country;

    @Column(name = "HouseNumber")
    private String houseNumber;

    @Column(name = "PhoneNumber")
    private String phoneNumber;

    @Column(name = "Email")
    private String email;

    @Basic(optional = false)
    @Column(name = "DateRegistred")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegistred;

    @Basic(optional = false)
    @Column(name = "Gender")
    private int gender;

    @Basic(optional = false)
    @Column(name = "Nationality")
    private int nationality;

    @Column(name = "PersonalNationalNumber")
    private String personalNationalNumber;

    @Column(name = "BirthPlace")
    private String birthPlace;

    @Column(name = "LandlineNumber")
    private String landlineNumber;

    @Column(name = "MailParent")
    private String mailParent;

    @Basic(optional = false)
    @Column(name = "Discriminator")
    private String discriminator;

    @Column(name = "Rank")
    private int rank;

    @ManyToMany(mappedBy = "userCollection")
    private Collection<Session> sessionCollection;

    @OneToMany(mappedBy = "memberUserId")
    private Collection<Comment> commentCollection;

    @OneToMany(mappedBy = "user")
    private Collection<Score> scores;

    @OneToMany(mappedBy = "userId")
    private Collection<Comment> commentCollection1;

    @JoinColumn(name = "CityPostalcode", referencedColumnName = "Postalcode")
    @ManyToOne(optional = false)
    private City cityPostalcode;

    @JoinColumn(name = "FormulaId", referencedColumnName = "FormulaId")
    @ManyToOne
    private Formula formulaId;

    @JoinColumn(name = "SessionId", referencedColumnName = "SessionId")
    @ManyToOne
    private Session sessionId;

    @JoinColumn(name = "SessionId1", referencedColumnName = "SessionId")
    @ManyToOne
    private Session sessionId1;

    @OneToMany(mappedBy = "teacherUserId")
    private Collection<Session> sessionCollection1;

    //endregion

    //properties for table
    @Transient
    private SimpleStringProperty familyNameProperty = new SimpleStringProperty();
    @Transient
    private SimpleStringProperty scoreProperty = new SimpleStringProperty();
    @Transient
    private SimpleStringProperty firstNameProperty = new SimpleStringProperty();
    @Transient
    private  SimpleStringProperty KyuProperty = new SimpleStringProperty();
    @Transient
    private SimpleStringProperty dateOfBirthProperty = new SimpleStringProperty();
    @Transient
    private SimpleStringProperty telephoneProperty = new SimpleStringProperty();
    @Transient
    private SimpleStringProperty emailProperty = new SimpleStringProperty();
    @Transient
    private SimpleStringProperty discriminatorProperty = new SimpleStringProperty();

    public User() {
    }

    public User(Integer userId) {
        this.userId = userId;
    }

    public User(UserDTO u)
    {
        this.setFirstName(u.getFirstName());
        this.setName(u.getName());
        this.setBirthPlace(u.getBirthPlace());
        this.setPersonalNationalNumber(u.getPersonalNationalNumber());
        this.setDateOfBirth(u.getDateOfBirth());
        this.setNationality(u.getNationality());
        this.setGender(u.getGender());
        this.setFormulaId(u.getFormulaId());
        this.setRank(u.getRank());
        this.setDiscriminator(u.getDiscriminator());
        this.setScores(u.getScores());
        this.setStreet(u.getStreet());
        this.setHouseNumber(u.getHouseNumber());
        this.setCountry(u.getCountry());
        this.setCityPostalcode(u.getCityPostalcode());
        this.setEmail(u.getEmail());
        this.setLandlineNumber(u.getLandlineNumber());
        this.setMailParent(u.getMailParent());
        this.setPhoneNumber(u.getPhoneNumber());
        this.setDateRegistred(u.getDateRegistred());

    }

    public User(Integer userId, Date dateOfBirth, int country, Date dateRegistred, int gender, int nationality, String discriminator) {
        this.userId = userId;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.dateRegistred = dateRegistred;
        this.gender = gender;
        this.nationality = nationality;
        this.discriminator = discriminator;
    }

    //region Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.trim().isEmpty() || name.length() >= 100)
            throw new IllegalArgumentException("Familienaam is verplicht (max 100 karakters)");
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName.trim().isEmpty() || firstName.length() >= 100)
            throw new IllegalArgumentException("Voornaam is verplicht (max 100 karakters)");
        this.firstName = firstName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        if (dateOfBirth.after(new Date()))
            throw new IllegalArgumentException("Geboortedatum moet in het verleden liggen");
        this.dateOfBirth = dateOfBirth;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        if (street.trim().isEmpty())
            throw new IllegalArgumentException("Straat is verplicht");
        this.street = street;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        if (houseNumber.trim().isEmpty())
            throw new IllegalArgumentException("Huisnummer is verplicht");
        this.houseNumber = houseNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber.trim().isEmpty())
            throw new IllegalArgumentException("Telefoonnummer is verplicht");
        else if (phoneNumber.length() < 10 || phoneNumber.length() > 20 ||
                (!phoneNumber.startsWith("+") && !phoneNumber.startsWith("04")))
            throw new IllegalArgumentException("Geen geldig telefoonnummer");
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email.trim().isEmpty())
            throw new IllegalArgumentException("E-mailadres is verplicht");
        else if (!Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+[.][a-zA-Z]+", email))
            throw new IllegalArgumentException("Geen geldig E-mailadres");
        this.email = email;
    }

    public Date getDateRegistred() {
        return dateRegistred;
    }

    public void setDateRegistred(Date dateRegistred) {
        this.dateRegistred = dateRegistred;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getNationality() {
        return nationality;
    }

    public void setNationality(int nationality) {
        this.nationality = nationality;
    }

    public String getPersonalNationalNumber() {
        return personalNationalNumber;
    }

    public void setPersonalNationalNumber(String personalNationalNumber) {
        if (personalNationalNumber.trim().isEmpty() ||
                !Pattern.matches("[0-9]{2}[.][0-9]{2}[.][0-9]{2}[-][0-9]{3}[.][0-9]{2}", personalNationalNumber))
            throw new IllegalArgumentException("Ongeldig rijksregisternummer");
        this.personalNationalNumber = personalNationalNumber;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        if (birthPlace.trim().isEmpty() || birthPlace.length() > 100)
            throw new IllegalArgumentException("Geboorteplaats is verplicht (max 100 karakters");
        this.birthPlace = birthPlace;
    }

    public String getLandlineNumber() {
        return landlineNumber;
    }

    public void setLandlineNumber(String landlineNumber) {
        if (!landlineNumber.trim().isEmpty() && (landlineNumber.length() < 9 || landlineNumber.length() > 20))
            throw new IllegalArgumentException("Geen geldig nummer");
        this.landlineNumber = landlineNumber;
    }

    public String getMailParent() {
        return mailParent;
    }

    public void setMailParent(String mailParent) {
        if (!mailParent.trim().isEmpty() &&
                !Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+[.][a-zA-Z]+", mailParent))
            throw new IllegalArgumentException("Geen geldig E-mailadres van ouder");
        this.mailParent = mailParent;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Collection<Score> getScores(){return scores;}

    public int getTotaleScore(){
        return this.scores.stream().mapToInt(Score::getAmount).sum();
    }

    public void addScoreTotScores(Score score){
        this.scores.add(score);
    }

    public void removeScoreFromScores(Score score){
        this.scores.remove(score);
    }

    public void setScores(Collection<Score> score){this.scores = score;}

    public Collection<Session> getSessionCollection() {
        return sessionCollection;
    }

    public void setSessionCollection(Collection<Session> sessionCollection) {
        this.sessionCollection = sessionCollection;
    }

    public Collection<Comment> getCommentCollection() {
        return commentCollection;
    }

    public void setCommentCollection(Collection<Comment> commentCollection) {
        this.commentCollection = commentCollection;
    }

    public Collection<Comment> getCommentCollection1() {
        return commentCollection1;
    }

    public void setCommentCollection1(Collection<Comment> commentCollection1) {
        this.commentCollection1 = commentCollection1;
    }

    public City getCityPostalcode() {
        return cityPostalcode;
    }

    public void setCityPostalcode(City city) {
        if (city == null)
            throw new IllegalArgumentException("Geen geldige stad");
        else if (city.getPostalcode().length() != 4)
            throw new IllegalArgumentException("Postcode moet 4 cijfers bevatten");
        this.cityPostalcode = city;
    }

    public Formula getFormulaId() {
        return formulaId;
    }

    public void setFormulaId(Formula formulaId) {
        this.formulaId = formulaId;
    }

    public Session getSessionId() {
        return sessionId;
    }

    public void setSessionId(Session sessionId) {
        this.sessionId = sessionId;
    }

    public Session getSessionId1() {
        return sessionId1;
    }

    public void setSessionId1(Session sessionId1) {
        this.sessionId1 = sessionId1;
    }

    public Collection<Session> getSessionCollection1() {
        return sessionCollection1;
    }

    public void setSessionCollection1(Collection<Session> sessionCollection1) {
        this.sessionCollection1 = sessionCollection1;
    }
    //endregion

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return firstName + " " + name;
    }

    //properties zijn nodig voor een tabel
    public SimpleStringProperty familyNameProperty() {
        this.familyNameProperty = new SimpleStringProperty(name);
        return this.familyNameProperty;

    }

    public SimpleStringProperty scoreProperty(){
        int _score = scores.stream().mapToInt(Score::getAmount).sum();
        this.scoreProperty = new SimpleStringProperty(String.format("%s", _score));
        return this.scoreProperty;
    }


    public String dateFormatter(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int days = date.getDay();
        int months = date.getMonth();
        int years = cal.get(Calendar.YEAR);

        return String.format("%d/%d/%d ", years, months, days);
    }

    public SimpleStringProperty firstNameProperty() {
        this.firstNameProperty = new SimpleStringProperty(firstName);
        return this.firstNameProperty;
    }

    public SimpleStringProperty dateOfBirthProperty() {
        this.dateOfBirthProperty = new SimpleStringProperty(dateFormatter(dateOfBirth));
        return this.dateOfBirthProperty;
    }

    public SimpleStringProperty KyuProperty(){
        if(Rank.getById(rank) != null)
            this.KyuProperty = new SimpleStringProperty(String.format("%s",Rank.getById(rank)));
        else this.KyuProperty = new SimpleStringProperty("Niet van toepassing");

        return  this.KyuProperty;
    }

    public SimpleStringProperty discriminatorProperty(){
        this.discriminatorProperty = new SimpleStringProperty(discriminator);

        if(discriminator.equals("Member"))
            this.discriminatorProperty = new SimpleStringProperty("Lid");
        else if(discriminator.equals("Admin"))
            this.discriminatorProperty = new SimpleStringProperty("Administrator");
        else if(discriminator.equals("Teacher"))
            this.discriminatorProperty = new SimpleStringProperty("Trainer");
        else{
            this.discriminatorProperty = new SimpleStringProperty("Onbekend");
        }

        return this.discriminatorProperty;
    }

    public SimpleStringProperty telephoneProperty() {
        this.telephoneProperty = new SimpleStringProperty(phoneNumber);
        return this.telephoneProperty;
    }

    public SimpleStringProperty emailProperty() {
        this.emailProperty = new SimpleStringProperty(email);
        return this.emailProperty;
    }


}
