package org.opendatakit.survey.espresso;

/**
 * Created by clarice on 7/19/17.
 */

import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import android.webkit.WebView;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.opendatakit.data.ColorRule;
import org.opendatakit.survey.utilities.FormInfo;

public class ODKMatcher {
  public static Matcher<View> withSize(final int size) {
    return new BoundedMatcher<View, ListView>(ListView.class) {
      private int listSize;

      @Override
      public void describeTo(Description description) {
        description.appendText("List size should be: " + size + "; Got: " + this.listSize);
      }

      @Override
      protected boolean matchesSafely(ListView item) {
        this.listSize = item.getCount();

        return this.listSize == size;
      }
    };
  }

  public static Matcher<View> withUrl(final Matcher<String> url) {
    return new BoundedMatcher<View, WebView>(WebView.class) {
      @Override
      public void describeTo(Description description) {
        description.appendText("with URL: ");
        url.describeTo(description);
      }

      @Override
      protected boolean matchesSafely(WebView item) {
        return url.matches(item.getUrl());
      }
    };
  }

//  public static Matcher<TableNameStruct> withTable(final String tableId) {
//    return new TypeSafeMatcher<TableNameStruct>() {
//      private TableNameStruct tableName;
//
//      @Override
//      protected boolean matchesSafely(TableNameStruct item) {
//        this.tableName = item;
//
//        return item.getTableId().equals(tableId);
//      }
//
//      @Override
//      public void describeTo(Description description) {
//        description.appendText("Table Id should be: " + tableName.getTableId());
//        description.appendText("; Got: " + tableId);
//      }
//    };
//  }

    public static Matcher<FormInfo> withForm(final String formId) {
    return new TypeSafeMatcher<FormInfo>() {
      private FormInfo formIdName;

      @Override
      protected boolean matchesSafely(FormInfo item) {
        this.formIdName = item;

        return item.formId.equals(formId);
      }

      @Override
      public void describeTo(Description description) {
        description.appendText("Table Id should be: " + formIdName.formId);
        description.appendText("; Got: " + formId);
      }
    };
  }

  public static Matcher<ColorRule> withColorRule(final ColorRule rule) {
    return new TypeSafeMatcher<ColorRule>() {
      @Override
      protected boolean matchesSafely(ColorRule rule2) {
        return rule2.equalsWithoutId(rule);
      }

      @Override
      public void describeTo(Description description) {
        description.appendText("Expected: " + rule.toString());
      }
    };
  }

//  public static Matcher<Intent> hasTable(final String tableId, final String formId,
//                                         final String instanceId) {
//    String scheme = "content://";
//    String host = "org.opendatakit.provider.forms";
//    String path = "/" + APP_NAME + "/" + tableId + "/" + formId + "/";
//
//    Matcher<Intent> partial = allOf(hasAction("android.intent.action.EDIT"),
//            toPackage(SURVEY_PKG_NAME));
//
//    if (instanceId == null) {
//      return allOf(partial, hasData(allOf(hasHost(host), hasPath(path))));
//    } else {
//      return allOf(partial, hasData(scheme + host + path + "#instanceId=" + instanceId));
//    }
//  }
}