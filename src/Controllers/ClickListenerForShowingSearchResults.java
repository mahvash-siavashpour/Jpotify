package Controllers;

import GUI.DialogBoxList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClickListenerForShowingSearchResults implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if(ClickListenerForSearchButton.getSearchResults()!=null) {
            DialogBoxList searchResults=new DialogBoxList("SearchResults",new SelectPlayFromSearchMenu());
        }

    }
}
