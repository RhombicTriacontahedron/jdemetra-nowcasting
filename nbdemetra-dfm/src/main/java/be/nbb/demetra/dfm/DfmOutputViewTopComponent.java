/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nbb.demetra.dfm;

import ec.nbdemetra.ui.NbComponents;
import ec.nbdemetra.ws.WorkspaceItem;
import ec.tss.dfm.DfmDocument;
import ec.tss.dfm.VersionedDfmDocument;
import ec.ui.view.tsprocessing.DefaultProcessingViewer;
import static ec.ui.view.tsprocessing.DefaultProcessingViewer.Type.NONE;
import ec.util.various.swing.FontAwesome;
import static ec.util.various.swing.FontAwesome.FA_COGS;
import static ec.util.various.swing.FontAwesome.FA_EXCLAMATION_TRIANGLE;
import static ec.util.various.swing.FontAwesome.FA_INFO_CIRCLE;
import static ec.util.various.swing.FontAwesome.FA_SPINNER;
import java.awt.BorderLayout;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//be.nbb.demetra.dfm//DfmOutputView//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "DfmOutputViewTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@Messages({
    "CTL_DfmOutputViewAction=DfmOutputView",
    "CTL_DfmOutputViewTopComponent=DfmOutputView Window",
    "HINT_DfmOutputViewTopComponent=This is a DfmOutputView window"
})
public final class DfmOutputViewTopComponent extends AbstractDfmDocumentTopComponent {

    private final XLabel label;

    private final DefaultProcessingViewer<DfmDocument> processingViewer;

    public DfmOutputViewTopComponent() {
        this(null, new DfmController());
    }

    DfmOutputViewTopComponent(WorkspaceItem<VersionedDfmDocument> document, DfmController controller) {
        super(document, controller);
        initComponents();
        this.label = new XLabel();

        this.processingViewer = new DefaultProcessingViewer<DfmDocument>(NONE) {
        };

        processingViewer.setHeaderVisible(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    void writeProperties(java.util.Properties p) {
    }

    void readProperties(java.util.Properties p) {
    }

    @Override
    protected void onDfmStateChange() {
        updateChart();
        super.onDfmStateChange();
    }

    @Override
    public JComponent getToolbarRepresentation() {
        JToolBar toolbar = NbComponents.newInnerToolbar();
        toolbar.addSeparator();

//        JButton copy = toolbar.add(CopyCommand.INSTANCE.toAction(this));
//        copy.setIcon(DemetraUiIcon.EDIT_COPY_16);
//        copy.setDisabledIcon(ImageUtilities.createDisabledIcon(copy.getIcon()));
        return toolbar;
    }

    private void switchTo(Component c) {
        removeAll();
        add(c, BorderLayout.CENTER);
    }

    private void updateChart() {
        switch (controller.getDfmState()) {
            case DONE:
                processingViewer.setDocument(getDocument().getElement().getCurrent());
                switchTo(processingViewer);
//                    switchTo(label.with(FA_EXCLAMATION_TRIANGLE, "No data produced"));
                break;
            case CANCELLED:
                switchTo(label.with(FA_INFO_CIRCLE, "Cancelled"));
                break;
            case CANCELLING:
                switchTo(label.with(FA_SPINNER, "Cancelling"));
                break;
            case FAILED:
                switchTo(label.with(FA_EXCLAMATION_TRIANGLE, "Failed"));
                break;
            case READY:
                processingViewer.setDocument(null);
                switchTo(label.with(FA_COGS, "Ready"));
                break;
            case STARTED:
                switchTo(label.with(FA_SPINNER, "Started"));
                break;
        }
    }

    private static final class XLabel extends JLabel {

        public XLabel() {
            setOpaque(true);
            JList resource = new JList();
            setBackground(resource.getSelectionForeground());
            setForeground(resource.getSelectionBackground());
            setFont(resource.getFont().deriveFont(resource.getFont().getSize2D() * 2));
            setHorizontalAlignment(SwingConstants.CENTER);
            //
            setHorizontalTextPosition(JLabel.CENTER);
            setVerticalTextPosition(JLabel.BOTTOM);
        }

        public XLabel with(FontAwesome icon, String text) {
            setIcon(icon.getIcon(getForeground(), getFont().getSize2D() * 2));
            setText(text);
            return this;
        }
    }
}
