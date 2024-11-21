package GUI.HR.AddRemoveEmp;

import Database.DBRequests;
import GUI.General.GUIInfo;
import SystemAndGeneral.SystemInfo;
import java.awt.*;
import javax.swing.*;

/**
 * This class <b>HRRemoveEmployee</b> holds all the components, styling, and logic for
 * the HR remove employee page.
 */
public class HRRemoveEmployee {

    private static JPanel menu;
    private final JPanel quickmenu, content;
    private final JButton switchType, logout, userSearch, addRemoveEmployee, searchConfirm, deleteButton, holidays, meetings, overtime;
    private final JLabel id, firstName, lastName, address, age, gender, salary, infoInputLabel;
    private static JLabel idInfo, firstNameInfo, lastNameInfo, addressInfo, ageInfo, genderInfo, salaryInfo;
    private final JLabel welcome, title;
    private static JTextField infoInput;
    private static boolean refresh = false;

    /**
     * Initializes the code for the HR remove employee page
     */
    public HRRemoveEmployee() {

        // Panels
        menu = new JPanel();
        menu.setLayout(new BorderLayout());

        quickmenu = new JPanel();
        quickmenu.setLayout(null);

        content = new JPanel();
        content.setLayout(null);

        // Buttons
        switchType = createButton("Switch to \npersonal");
        userSearch = createButton("Employee\nSearch/Edit");
        addRemoveEmployee = createButton("Add/Remove\nEmployee");
        holidays = createButton("View/Change\nHolidays");
        meetings = createButton("View/Change\nMeetings");
        overtime = createButton("View/Change\nOvertime");
        logout = new JButton("Logout");
        searchConfirm = new JButton("Search");
        deleteButton = new JButton("Delete");

        // Text Fields
        infoInput = new JTextField();

        // Labels
        welcome = new JLabel("HR MENU", SwingConstants.CENTER);
        title = new JLabel("<html><h3 align='center'>Remove Employee</h3></html>", SwingConstants.CENTER);

        infoInputLabel = new JLabel("Enter ID:");
        id = new JLabel("ID:");
        firstName = new JLabel("First Name:");
        lastName = new JLabel("Last Name:");
        address = new JLabel("Address:");
        age = new JLabel("Age:");
        gender = new JLabel("Gender:");
        salary = new JLabel("Salary:");

        idInfo = new JLabel();
        firstNameInfo = new JLabel();
        lastNameInfo = new JLabel();
        addressInfo = new JLabel();
        ageInfo = new JLabel();
        genderInfo = new JLabel();
        salaryInfo = new JLabel();

        // Action Listeners
        setupActionListeners();

        // Quickbar positioning & adding
        setupQuickMenu();

        // Content positioning & adding
        setupContent();

        // Add content to the menu
        menu.add(content, BorderLayout.CENTER);
        menu.add(quickmenu, BorderLayout.WEST);
    }

    private JButton createButton(String text) {
        JButton button = new JButton("<html><style>p {text-align: center;}</style> <p>" + text.replaceAll("\\n", "<br>") + "</p></html>");
        return button;
    }

    private void setupActionListeners() {
        logout.addActionListener(listener -> GUIInfo.getCL().show(GUIInfo.getCont(), "Login"));

        switchType.addActionListener(listener -> {
            try {
                if (DBRequests.isEmployee(SystemInfo.getID()).equals("1")) {
                    GUIInfo.getCL().show(GUIInfo.getCont(), "NonHRMenu");
                }
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        userSearch.addActionListener(listener -> GUIInfo.getCL().show(GUIInfo.getCont(), "HRMenu"));
        addRemoveEmployee.addActionListener(listener -> GUIInfo.getCL().show(GUIInfo.getCont(), "HRAddRemove"));
        holidays.addActionListener(listener -> GUIInfo.getCL().show(GUIInfo.getCont(), "HRHolidayHome"));
        meetings.addActionListener(listener -> GUIInfo.getCL().show(GUIInfo.getCont(), "HRMeetingsHomeMenu"));
        overtime.addActionListener(listener -> GUIInfo.getCL().show(GUIInfo.getCont(), "HROvertimeHomeMenu"));

        searchConfirm.addActionListener(listener -> {
            try {
                int inputValue = Integer.parseInt(infoInput.getText());
                if (DBRequests.isEmployee(inputValue).equals("1") || DBRequests.isEmployee(inputValue).equals("0")) {
                    idInfo.setText(String.valueOf(inputValue));
                    firstNameInfo.setText(DBRequests.getFirstName(inputValue));
                    lastNameInfo.setText(DBRequests.getLastName(inputValue));
                    addressInfo.setText(DBRequests.getAddress(inputValue));
                    ageInfo.setText(DBRequests.getAge(inputValue));
                    genderInfo.setText(DBRequests.getGender(inputValue));
                    salaryInfo.setText(DBRequests.getSalary(inputValue));
                }
            } catch (Exception e) {
                HRRemoveRefresh();
                idInfo.setText("Employee does not exist");
            }
        });

        deleteButton.addActionListener(listener -> {
            try {
                int inputValue = Integer.parseInt(infoInput.getText());
                DBRequests.deleteEmployee(inputValue);
                HRRemoveRefresh();
                idInfo.setText("Employee " + inputValue + " has been deleted");
            } catch (Exception e) {
                HRRemoveRefresh();
                idInfo.setText("Employee does not exist");
            }
        });
    }

    private void setupQuickMenu() {
        quickmenu.setPreferredSize(new Dimension(230, 500));
        quickmenu.setBackground(Color.GRAY);

        logout.setBounds(0, 0, 65, 20);
        quickmenu.add(logout);

        welcome.setBounds(0, 15, 230, 15);
        quickmenu.add(welcome);

        switchType.setBounds(60, 415, 110, 45);
        quickmenu.add(switchType);

        userSearch.setBounds(50, 55, 135, 45);
        quickmenu.add(userSearch);

        addRemoveEmployee.setBounds(50, 105, 135, 45);
        quickmenu.add(addRemoveEmployee);

        holidays.setBounds(50, 155, 135, 45);
        quickmenu.add(holidays);

        meetings.setBounds(50, 205, 135, 45);
        quickmenu.add(meetings);

        overtime.setBounds(50, 255, 135, 45);
        quickmenu.add(overtime);
    }

    private void setupContent() {
        content.setPreferredSize(new Dimension(800, 500));

        title.setBounds(0, 10, 570, 25);
        content.add(title);

        infoInputLabel.setBounds(10, 40, 100, 25);
        content.add(infoInputLabel);

        infoInput.setBounds(120, 40, 160, 25);
        content.add(infoInput);

        searchConfirm.setBounds(290, 40, 80, 25);
        content.add(searchConfirm);

        deleteButton.setBounds(370, 40, 100, 25);
        content.add(deleteButton);

        // Horizontal Layout for Employee Information
        int startY = 80; // Starting y-coordinate for all labels
        int labelWidth = 100; // Width of the labels
        int xSpacing = 20; // Space between labels

        // Labels positioning
        id.setBounds(10, startY, labelWidth, 15);
        content.add(id);

        firstName.setBounds(10 + labelWidth + xSpacing, startY, labelWidth, 15);
        content.add(firstName);

        lastName.setBounds(10 + 2 * (labelWidth + xSpacing), startY, labelWidth, 15);
        content.add(lastName);

        address.setBounds(10 + 3 * (labelWidth + xSpacing), startY, labelWidth, 15);
        content.add(address);

        age.setBounds(10 + 4 * (labelWidth + xSpacing), startY, labelWidth, 15);
        content.add(age);

        gender.setBounds(10 + 5 * (labelWidth + xSpacing), startY, labelWidth, 15);
        content.add(gender);

        salary.setBounds(10 + 6 * (labelWidth + xSpacing), startY, labelWidth, 15);
        content.add(salary);

        // Info positioning
        int infoStartY = startY + 20; // Adjusted y-coordinate for info labels

        // ID info positioning
        idInfo.setBounds(10, infoStartY, labelWidth, 15);
        content.add(idInfo);

        // First Name info positioning
        firstNameInfo.setBounds(10 + labelWidth + xSpacing, infoStartY, labelWidth, 15);
        content.add(firstNameInfo);

        // Last Name info positioning
        lastNameInfo.setBounds(10 + 2 * (labelWidth + xSpacing), infoStartY, labelWidth, 15);
        content.add(lastNameInfo);

        // Address info positioning
        addressInfo.setBounds(10 + 3 * (labelWidth + xSpacing), infoStartY, labelWidth, 15);
        content.add(addressInfo);

        // Age info positioning
        ageInfo.setBounds(10 + 4 * (labelWidth + xSpacing), infoStartY, labelWidth, 15);
        content.add(ageInfo);

        // Gender info positioning
        genderInfo.setBounds(10 + 5 * (labelWidth + xSpacing), infoStartY, labelWidth, 15);
        content.add(genderInfo);

        // Salary info positioning
        salaryInfo.setBounds(10 + 6 * (labelWidth + xSpacing), infoStartY, labelWidth, 15);
        content.add(salaryInfo);
    }

    /**
     * This method <b>HRRemoveRefresh</b> clears the fields.
     */
    public static void HRRemoveRefresh() {
        // Clear previous text
        idInfo.setText("");
        firstNameInfo.setText("");
        addressInfo.setText("");
        ageInfo.setText("");
        genderInfo.setText("");
        salaryInfo.setText("");
        refresh = true;
    }

    /**
     * This method <b>getPage</b> returns the <b>HRRemoveEmployee</b> JPanel.
     *
     * @return HRRemoveEmployee JPanel
     */
    public static JPanel getPage() {
        return menu;
    }

    /**
     * This method <b>clearEntry</b> is used to clear the JTextField after leaving
     * this page.
     */
    public static void clearEntry() {
        infoInput.setText("");
    }
}