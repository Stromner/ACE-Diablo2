package ace.diablo.two.ui.components;

import ace.diablo.two.core.events.DataLayerInitiatedEvent;
import ace.diablo.two.ui.components.panels.CharacterPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ace.diablo.two.ui.components.panels.CheatPanel;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
@ConditionalOnProperty(name = "editor.live.boot")
public class ContentPanel extends JPanel {
    @Autowired
    private CharacterPanel characterPanel;
    @Autowired
    private CheatPanel cheatPanel;
    private JTabbedPane sidebar;

    @PostConstruct
    private void init() {
        setLayout(new GridLayout());
        sidebar = new JTabbedPane(JTabbedPane.LEFT);
        sidebar.addTab("Character", characterPanel);
        sidebar.addTab("Skills", new JPanel());
        sidebar.addTab("Cheats", cheatPanel);
    }

    @EventListener
    public void onDatabaseInitiatedEvent(DataLayerInitiatedEvent event) {
        removeAll();
        try {
            characterPanel.renderData();
            cheatPanel.renderData();
            sidebar.setSelectedIndex(0);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        add(sidebar);
        revalidate();
    }
}
