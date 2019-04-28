package com.alevlash.smscommander;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.SmsManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class SmsCommanderTest {

    private static final String PHONE_NUMBER = "123";
    private static final Uri URI = Uri.EMPTY;

    private SmsCommander _smsCommander;

    private SmsManager _mockSmsManager;
    private Context _mockContext;
    private ContentResolver _mockContentResolver;
    private Cursor _mockCursor;

    @Before
    public void setup() {
        _mockSmsManager = Mockito.mock(SmsManager.class);
        _mockContext = Mockito.mock(Context.class);
        _mockContentResolver = Mockito.mock(ContentResolver.class);
        _mockCursor = Mockito.mock(Cursor.class);

        Mockito.when(_mockContext.getContentResolver()).thenReturn(_mockContentResolver);

        _smsCommander = Mockito.spy(new SmsCommander(_mockSmsManager));
    }

    @Test
    public void getContactDisplayNameByNumber_contactExists_returnsName() {
        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};
        int index = 99;
        String name = "test";

        Mockito.doReturn(URI).when(_smsCommander).getUri(PHONE_NUMBER);
        Mockito.doReturn(_mockCursor).when(_mockContentResolver).query(Mockito.eq(URI), Mockito.eq(projection), Mockito.eq((String) null), Mockito.eq((String[]) null), Mockito.eq((String) null));
        Mockito.doReturn(true).when(_mockCursor).moveToNext();
        Mockito.doReturn(index).when(_mockCursor).getColumnIndex(ContactsContract.Data.DISPLAY_NAME);
        Mockito.doReturn(name).when(_mockCursor).getString(index);

        String actualName = _smsCommander.getContactDisplayNameByNumber(PHONE_NUMBER, _mockContext);
        Assert.assertEquals(name, actualName);

        Mockito.verify(_mockCursor).close();
    }

    @Test
    public void getContactDisplayNameByNumber_contactDoesNotExist_returnsNull() {
        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};

        Mockito.doReturn(URI).when(_smsCommander).getUri(PHONE_NUMBER);
        Mockito.doReturn(_mockCursor).when(_mockContentResolver).query(Mockito.eq(URI), Mockito.eq(projection), Mockito.eq((String) null), Mockito.eq((String[]) null), Mockito.eq((String) null));
        Mockito.doReturn(false).when(_mockCursor).moveToNext();

        String name = _smsCommander.getContactDisplayNameByNumber(PHONE_NUMBER, _mockContext);
        Assert.assertNull(name);

        Mockito.verify(_mockCursor).close();
    }

}