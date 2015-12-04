package edu.udcs.udromeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import edu.udcs.udromeapp.currency.ConverterActivity;
import edu.udcs.udromeapp.datadisplay.DataDisplayActivity;


public class LandingFragment extends Fragment {

    enum MiniApps {
        TODO,
        PACKING,
        MAPS,
        FLIGHT_FACTS,
        JOURNAL_AID,
        TRANSLATIONS,
        CURRENCY_CONVERTER
    }

    public final IconText[] iconTexts = {new IconText(android.R.drawable.ic_menu_directions, MiniApps.TODO, "To do"),
            new IconText(android.R.drawable.checkbox_on_background, MiniApps.PACKING, "Packing"),
            new IconText(android.R.drawable.ic_menu_compass, MiniApps.MAPS, "Maps"),
            new IconText(android.R.drawable.ic_dialog_info, MiniApps.FLIGHT_FACTS, "Flight Facts"),
            new IconText(android.R.drawable.edit_text, MiniApps.JOURNAL_AID, "Journal Aid"),
            new IconText(android.R.drawable.ic_menu_compass, MiniApps.TRANSLATIONS, "Translations"),
            new IconText(android.R.drawable.ic_dialog_dialer, MiniApps.CURRENCY_CONVERTER, "Currencies")};

    private GridView mLandingOptions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_landing, container, false);

        mLandingOptions = (GridView) view.findViewById(R.id.landing_buttons);

        mLandingOptions.setAdapter(new LandingAdapter(iconTexts));

        return view;
    }


    class IconText {

        int icon;
        MiniApps tag;
        String text;

        public IconText(int icon, MiniApps tag, String text) {
            this.icon = icon;
            this.tag = tag;
            this.text = text;
        }

        public IconText(int icon, MiniApps tag, int text){
            this(icon, tag, getString(text));
        }

        public int getIcon() {
            return icon;
        }

        public MiniApps getTag(){
            return tag;
        }

        public String getText() {
            return text;
        }
    }


    private class LandingAdapter extends BaseAdapter {

        private IconText[] items;

        public LandingAdapter(IconText[] items) {
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int position) {
            return items[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.app_launcher, parent, false);

            ((Button) convertView).setText(((IconText) getItem(position)).getText());
            ((Button) convertView).setCompoundDrawablesWithIntrinsicBounds(0, ((IconText) getItem(position)).getIcon(), 0, 0);
            convertView.setTag(((IconText) getItem(position)).getTag());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchMiniApp((MiniApps) v.getTag());
                }
            });

            return convertView;
        }
    }

    protected void launchMiniApp(MiniApps id){
        Intent intent = null;

        switch (id) {
            case TODO:
                intent = new Intent(getActivity(), DataDisplayActivity.class);
                break;
            case PACKING:
                break;
            case MAPS:
                break;
            case FLIGHT_FACTS:
                break;
            case JOURNAL_AID:
                break;
            case TRANSLATIONS:
                intent = new Intent(getActivity(), TranslationsActivity.class);
                break;
            case CURRENCY_CONVERTER:
                intent = new Intent(getActivity(), ConverterActivity.class);
                break;
            default:
                throw new EnumConstantNotPresentException(MiniApps.class, "A constant is either missing from MiniApps or is not checked for in the LandingFragment.launchMiniApp switch");
        }

        startActivity(intent);
    }

}
