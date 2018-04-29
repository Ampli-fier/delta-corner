The contents of this folder documents the study to use more than one account on Android.

With the help of _Core Commands_ one can add additional accounts - resulting in additional database files, eg.:

```
open /data/data/com.b44t.messenger.beta/files/office.db
```

# Switch

The changes in `ApplicationLoader.java` will use a different database file on every startup.
The maximun recognized amount of databases (for switching) is four.

This is the folder structure with two accounts configured:

```
localhost com.b44t.messenger.beta # ll
drwxr-x--x    5 u0_a175      u0_a175           4096 Apr 28 16:25 .
drwxrwx--x  124 system       system            8192 Apr 15 22:41 ..
drwxrwx--x    2 u0_a175      u0_a175           4096 Apr 24 17:40 cache
drwxrwxrwx    4 u0_a175      u0_a175           4096 Apr 28 16:26 files
-rw-------    1 u0_a175      u0_a175              1 Apr 28 16:26 lastused
lrwxrwxrwx    1 install      install             43 Apr 28 16:23 lib -> /data/app/com.b44t.messenger.beta-2/lib/arm
drwxrwx--x    2 u0_a175      u0_a175           4096 Apr 27 23:57 shared_prefs
localhost com.b44t.messenger.beta # ll files/
drwxrwxrwx    4 u0_a175      u0_a175           4096 Apr 28 16:26 .
drwxr-x--x    5 u0_a175      u0_a175           4096 Apr 28 16:25 ..
-rw-------    1 u0_a175      u0_a175         565248 Apr 28 16:25 messenger.db
drwx------    2 u0_a175      u0_a175           4096 Apr 24 22:58 messenger.db-blobs
-rw-------    1 u0_a175      u0_a175        3330048 Apr 28 16:26 office.db
drwx------    2 u0_a175      u0_a175           4096 Apr 27 15:37 office.db-blobs
localhost com.b44t.messenger.beta #
```

The file `lastused` contains the information about the database that was opened last.
The order of databases follows the alphabetical order when using the `ls` command.

The study should be working since v0.11.x minimum.

# Restart

The changes in `ChatlistActivity.java` allow a quick restart of Delta Chat by adding a _Restart_ Option to the main menu.

It was intended to _Exit_ the app, but on my phone (Cyanogen Mod 12.1 = Android 5.1.1) ActivityManager restarted the app automatically, which fits to the use case :)

```
39:59.349 30810 30810 D DeltaChat: Calling Mailbox Close
39:59.355 30810 30810 I DeltaChat: T1: Stopping IMAP-watch-thread...
39:59.360 30810 30810 I DeltaChat: T1: Interrupting IDLE for disconnecting...
39:59.365 30810 30849 I DeltaChat: T4: IMAP-heartbeat-thread ended.
39:59.365 30810 30849 I DeltaChat: T4: Detaching C-thread from Java VM...
39:59.397 30810 30848 I DeltaChat: T3: IDLE interrupted.
39:59.397 30810 30848 I DeltaChat: T3: Detaching C-thread from Java VM...
39:59.400 30810 30810 I DeltaChat: T1: IMAP-watch-thread stopped.
39:59.403 30810 30810 I DeltaChat: T1: Disconnecting...
39:59.409 30810 30810 I DeltaChat: T1: Disconnect done.
39:59.413 30810 30810 I DeltaChat: T1: Database closed.
39:59.914 30810 30810 D DeltaChat: Kill my PID: 30810
39:59.915 30810 30810 I Process : Sending signal. PID: 30810 SIG: 9
40:00.042  2220  2358 I WindowState: WIN DEATH: Window{c1e517e u0 com.b44t.messenger.beta/com.b44t.messenger.LaunchActivity}
40:00.053  2220  2358 W WindowManager: Force-removing child win Window{3b963218 u0 PopupWindow:22f1610b} from container Window{c1e517e u0 com.b44t.messenger.beta/com.b44t.messenger.LaunchActivity}
40:00.098  2220  2675 I WindowState: WIN DEATH: null
40:00.109  2220  2235 I ActivityManager: Process com.b44t.messenger.beta (pid 30810) has died
40:00.110  2220  2235 W ActivityManager: Scheduling restart of crashed service com.b44t.messenger.beta/com.b44t.messenger.KeepAliveService in 1000ms
40:00.116  2220  2235 W ActivityManager: Force removing ActivityRecord{20415cff u0 com.b44t.messenger.beta/com.b44t.messenger.LaunchActivity t31130}: app died, no saved state
40:00.356  2220  5652 W InputMethodManagerService: Got RemoteException sending setActive(false) notification to pid 30810 uid 10175
40:01.250  2220  2241 I ActivityManager: Start proc 30892:com.b44t.messenger.beta/u0a175 for service com.b44t.messenger.beta/com.b44t.messenger.KeepAliveService
```

Have to read [this](https://stackoverflow.com/questions/2033914/is-quitting-an-application-frowned-upon) thoroughly to understand how to exit an Android app "in a good way".
