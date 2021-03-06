package org.xmind.ui.internal.actions;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.menus.IWorkbenchContribution;
import org.eclipse.ui.services.IServiceLocator;
import org.xmind.ui.internal.MindMapUIPlugin;

public class AllowOverlapsMenu extends ContributionItem implements
        IWorkbenchContribution {
    private boolean dirty = true;

    private IMenuListener menuListener = new IMenuListener() {
        public void menuAboutToShow(IMenuManager manager) {
            manager.markDirty();
            dirty = true;
        }
    };

    public boolean isDirty() {
        return dirty;
    }

    /**
     * Overridden to always return true and force dynamic menu building.
     */
    public boolean isDynamic() {
        return true;
    }

    public void fill(Menu menu, int index) {

        if (getParent() instanceof MenuManager) {
            ((MenuManager) getParent()).addMenuListener(menuListener);
        }

        if (!dirty) {
            return;
        }

        MenuManager manager = new MenuManager();
        fillMenu(manager);
        IContributionItem items[] = manager.getItems();
        if (items.length > 0) {
            for (int i = 0; i < items.length; i++) {
                items[i].fill(menu, index++);
            }
        }
        dirty = false;
    }

    private void fillMenu(MenuManager manager) {
        IPreferenceStore prefStore = MindMapUIPlugin.getDefault()
                .getPreferenceStore();
        AllowOverlapsAction allowOverlapsAction = new AllowOverlapsAction(
                prefStore);
        manager.add(allowOverlapsAction);
    }

    public void initialize(IServiceLocator serviceLocator) {
    }

}
