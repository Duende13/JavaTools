import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class SplitPaneDemo extends JFrame {

    JSplitPane splitPane;
    JPanel leftListPanel, rightListPanel;
    Container contentPane;
    String addString = "Add";
    String removeString = "Remove";
    
    String listData[] = {"duke","duke.running","duke2","dukeMagnify","dukeplug","dukeSnooze","dukeWave","dukeWaveRed","cow"};
    
    SplitPaneDemo(){
        super("SplitPane Demo !");
        DefaultListModel leftListModel = new DefaultListModel();
        DefaultListModel rightListModel = new DefaultListModel();
        for (String item : listData) {
            leftListModel.addElement(item);
        }
        JList leftList = new JList(leftListModel);
        leftList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        leftList.setSelectedIndex(0);
        leftList.setVisibleRowCount(30);
        leftList.addListSelectionListener(new ListSelectionListener() {
            
            @Override
            public void valueChanged(ListSelectionEvent e) {  
                if(leftList.getSelectedValue() != null) {
                    rightListModel.removeAllElements();
                    // TODO: change for the values that you want to enter
                    rightListModel.addElement(leftList.getSelectedValue().toString());
                }  
            }
        });
        // Left side panel
        JScrollPane leftListScrollPane = new JScrollPane(leftList);
        
        // Add new item to the left list button
        JButton leftAddButton = new JButton(addString);
        JTextField leftValueName = new JTextField(10);
        AddListener leftAddListener = new AddListener(leftAddButton, leftList, leftListModel, leftValueName);
        leftAddButton.setActionCommand(addString);
        leftAddButton.addActionListener(leftAddListener);
        leftAddButton.setEnabled(false);

        // Remove item to the left list button
        JButton leftRemoveButton = new JButton(removeString);
        RemoveListener leftRemoveListener = new RemoveListener(leftList, leftListModel, leftRemoveButton);
        leftRemoveButton.setActionCommand(removeString);
        leftRemoveButton.addActionListener(leftRemoveListener);

        leftValueName.addActionListener(leftAddListener);
        leftValueName.getDocument().addDocumentListener(leftAddListener);

        // Right side panel
        JList rightList = new JList(rightListModel);
        JScrollPane rightListScrollPane = new JScrollPane(rightList);

        // Add button to add new item to right list
        JButton rightAddButton = new JButton(addString);
        JTextField rightValueName = new JTextField(10);
        AddListener rightAddListener = new AddListener(rightAddButton, rightList, rightListModel,rightValueName);
        rightAddButton.setActionCommand(addString);
        rightAddButton.addActionListener(rightAddListener);
        rightAddButton.setEnabled(false);
        
        // Remove item to the right list button
        JButton rightRemoveButton = new JButton(removeString);
        RemoveListener rightRemoveListener = new RemoveListener(rightList, rightListModel, rightRemoveButton);
        rightRemoveButton.setActionCommand(removeString);
        rightRemoveButton.addActionListener(rightRemoveListener);
        
        rightValueName.addActionListener(rightAddListener);
        rightValueName.getDocument().addDocumentListener(rightAddListener);

        // Create a panel that uses BoxLayout for the Left side.
        JPanel leftButtonPane = new JPanel();
        leftButtonPane.setLayout(new BoxLayout(leftButtonPane, BoxLayout.LINE_AXIS));
        leftButtonPane.add(leftRemoveButton);
        leftButtonPane.add(Box.createHorizontalStrut(5));
        leftButtonPane.add(new JSeparator(SwingConstants.VERTICAL));
        leftButtonPane.add(Box.createHorizontalStrut(5));
        leftButtonPane.add(leftValueName);
        leftButtonPane.add(leftAddButton);
        leftButtonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Create a panel that uses BoxLayout for the Right side.
        JPanel rightButtonPane = new JPanel();
        rightButtonPane.setLayout(new BoxLayout(rightButtonPane, BoxLayout.LINE_AXIS));
        rightButtonPane.add(rightRemoveButton);
        rightButtonPane.add(Box.createHorizontalStrut(5));
        rightButtonPane.add(new JSeparator(SwingConstants.VERTICAL));
        rightButtonPane.add(Box.createHorizontalStrut(5));
        rightButtonPane.add(rightValueName);
        rightButtonPane.add(rightAddButton);
        rightButtonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        
        // Adding Left ListPanel 
        leftListPanel = new JPanel(new BorderLayout());
        leftListPanel.add(leftListScrollPane);
        leftListPanel.add(leftButtonPane, BorderLayout.PAGE_END);
        
        // Adding Right  Pannel
        rightListPanel = new JPanel(new BorderLayout());
        rightListPanel.add(rightListScrollPane);
        rightListPanel.add(rightButtonPane, BorderLayout.PAGE_END);
        
        // Creating SplitPanel
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,false,new JScrollPane(leftListPanel),rightListPanel);
        splitPane.setDividerSize(10);
        splitPane.setOneTouchExpandable(true);

        contentPane = getContentPane();
        contentPane.add(splitPane);
        
        setSize(800, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
    class RemoveListener implements ActionListener {
        private JList list;
        private DefaultListModel listModel;
        private JButton removeButton;

        public RemoveListener(JList remList, DefaultListModel remListModel, JButton removeButton) {
            this.list = remList;
            this.listModel = remListModel;
            this.removeButton = removeButton;
        }
        public void actionPerformed(ActionEvent e) {
            // This method can be called only if
            // there's a valid selection
            // so go ahead and remove whatever's selected.
            
            
            int index = list.getSelectedIndex();
            listModel.remove(index);

            int size = listModel.getSize();

            if (size == 0) { // Nobody's left, disable firing.
                removeButton.setEnabled(false);

            } else { // Select an index.
                if (index == listModel.getSize()) {
                    // removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    // This listener is shared by the text field and the add button.
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;
        private JList list;
        private DefaultListModel listModel;
        private JTextField valueName;

        public AddListener(JButton button, JList listAdd, DefaultListModel listModelAdd, JTextField valueName) {
            this.button = button;
            this.list = listAdd;
            this.listModel = listModelAdd;
            this.valueName = valueName;
        }

        // Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String name = valueName.getText();

            // User didn't type in a unique name...
            if (name.equals("") || alreadyInList(name)) {
                // Toolkit.getDefaultToolkit().beep();
                valueName.requestFocusInWindow();
                valueName.selectAll();
                return;
            }

            int index = list.getSelectedIndex(); // get selected index
            if (index == -1) { // no selection, so insert at beginning
                index = 0;
            } else { // add after the selected item
                index++;
            }

            // listModel.insertElementAt(valueName.getText(), index);
            // If we just wanted to add to the end, we'd do this:
            listModel.addElement(valueName.getText());

            // Reset the text field.
            valueName.requestFocusInWindow();
            valueName.setText("");

            // Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        // This method tests for string equality. You could certainly
        // get more sophisticated about the algorithm. For example,
        // you might want to ignore white space and capitalization.
        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        // Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        // Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        // Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    // This method is required by ListSelectionListener.
    // public void valueChanged(ListSelectionEvent e) {
    //     if (e.getValueIsAdjusting() == false) {

    //         if (list.getSelectedIndex() == -1) {
    //             // No selection, disable fire button.
    //             removeButton.setEnabled(false);

    //         } else {
    //             // Selection, enable the fire button.
    //             removeButton.setEnabled(true);
    //         }
    //     }
    // }
    public static void main(String[] args) {
        new SplitPaneDemo();
    }

}