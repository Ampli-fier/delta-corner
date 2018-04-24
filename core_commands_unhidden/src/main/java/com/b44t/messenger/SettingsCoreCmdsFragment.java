/*******************************************************************************
 *
 *                              Delta Chat Android
 *                           (C) 2017 Björn Petersen
 *                    Contact: r10s@b44t.com, http://b44t.com
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program.  If not, see http://www.gnu.org/licenses/ .
 *
 *******************************************************************************
 *
 * File:    NameSettingsActivity.java
 * Purpose: Let the user configure his name
 *
 ******************************************************************************/

package com.b44t.messenger;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.b44t.messenger.ActionBar.ActionBar;
import com.b44t.messenger.ActionBar.ActionBarMenu;
import com.b44t.messenger.ActionBar.BaseFragment;
import com.b44t.messenger.Cells.EditTextCell;
import com.b44t.messenger.Cells.TextInfoCell;
import com.b44t.messenger.Components.BaseFragmentAdapter;
import com.b44t.messenger.Cells.HeaderCell;
import com.b44t.messenger.Components.LayoutHelper;


public class SettingsCoreCmdsFragment extends BaseFragment {

    // the list
    private ListAdapter listAdapter;

    private int         rowCommandHeadline, rowCommand, rowCommandInfo;
    private int         rowCount;

    private final int   ROWTYPE_INFO       = 0; // no gaps here!
    private final int   ROWTYPE_TEXT_ENTRY = 1;
    private final int   ROWTYPE_HEADLINE   = 2;

    private EditTextCell commandCell;

    private final static int done_button = 1;

    @Override
    public boolean onFragmentCreate() {
        super.onFragmentCreate();

        rowCount = 0;

        rowCommandHeadline = rowCount++;
        rowCommand         = rowCount++;
        rowCommandInfo     = rowCount++;

        return true;
    }

    @Override
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
    }

    @Override
    public View createView(Context context) {

        // create action bar
        actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        actionBar.setAllowOverlayTitle(true);
        actionBar.setTitle(context.getString(R.string.CoreCommandsHeader));
        actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() {
            @Override
            public void onItemClick(int id) {
                if (id == -1) {
                    finishFragment();
                } else if (id == done_button) {
                    runCommand();
                }
            }
        });

        ActionBarMenu menu = actionBar.createMenu();
        menu.addItemWithWidth(done_button, R.drawable.ic_done, AndroidUtilities.dp(56));

        // create object to hold the whole view
        fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) fragmentView;
        frameLayout.setBackgroundColor(0xfff0f0f0);

        // create the main layout list
        listAdapter = new ListAdapter(context);

        ListView listView = new ListView(context);
        listView.setDivider(null);
        listView.setDividerHeight(0);
        listView.setDrawSelectorOnTop(true);
        frameLayout.addView(listView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {
            }
        });

        return fragmentView;
    }

    private void runCommand() {
        if( commandCell != null ) {
            String cmd = commandCell.getValue().trim();
            if (!cmd.isEmpty()) {
                String execute_result = MrMailbox.cmdline(cmd);
                if (execute_result == null || execute_result.isEmpty()) {
                    execute_result = "ERROR: Unknown command.";
                }
                final String message = execute_result;
                /* copy function? + history? + other reply than ERROR?  */
                AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
                builder.setMessage(message);
                builder.setPositiveButton(R.string.OK, null);
                builder.setNeutralButton(R.string.CopyToClipboard, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AndroidUtilities.addToClipboard(message);
                    }
                });
                showDialog(builder.create());

                commandCell.setValueHintAndLabel("", "", "", true);
            }
        }
        }

    private class ListAdapter extends BaseFragmentAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            mContext = context;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEnabled(int i) {
            return (i == rowCommand);
        }

        @Override
        public int getCount() {
            return rowCount;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            int type = getItemViewType__(i);
            if (type == ROWTYPE_HEADLINE) {
                if (view == null) {
                    view = new HeaderCell(mContext);
                    view.setBackgroundColor(0xffffffff);
                }
                if (i == rowCommandHeadline) {
                    ((HeaderCell) view).setText(mContext.getString(R.string.CoreCommandsEnter));
                }
            }
            else if (type == ROWTYPE_TEXT_ENTRY) {
                if (i == rowCommand) {
                    if(commandCell==null) {
                        commandCell = new EditTextCell(mContext, false);
                        commandCell.getEditTextView().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                    }
                    view = commandCell;
                }
            }
            else if (type == ROWTYPE_INFO) {
                if (view == null) {
                    view = new TextInfoCell(mContext);
                }
                if( i==rowCommandInfo) {
                    ((TextInfoCell) view).setText(mContext.getString(R.string.CoreCommandsInfo));
                    view.setBackgroundResource(R.drawable.greydivider);
                }
            }
            return view;
        }

        @Override
        public int getItemViewType(int i) {
            return IGNORE_ITEM_VIEW_TYPE;
        }

        private int getItemViewType__(int i) {
            if (i == rowCommand) {
                return ROWTYPE_TEXT_ENTRY;
            }
            else if(i==rowCommandHeadline) {
                return ROWTYPE_HEADLINE;
            }
            return ROWTYPE_INFO;
        }


        @Override
        public int getViewTypeCount() {
            return 1; /* SIC! internally, we ingnore the type, each row has its own type--otherwise text entry stuff would not work */
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    }
}
