package com.example.ibo.musicplayerofficial.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ibo.musicplayerofficial.Adapters.ListViewAdapter;
import com.example.ibo.musicplayerofficial.Classes.Song;
import com.example.ibo.musicplayerofficial.R;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    //Declare variables
    ArrayList<Song> arrayList;
    ListView songListView;
    ListViewAdapter adapter;

    SongFragment songFragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //Actionbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Song list");

        //Find my listview
        songListView = view.findViewById(R.id.songListView);

        //create a new arraylist object
        arrayList = new ArrayList<>();

        //region Add songs (collapsed)
        arrayList.add(new Song("Akon", "-Dont matter", R.raw.akon_dontmatter, R.drawable.akon, "Konvict, konvict, konvict\n" +
                "Oh, oh, oh, ooh, oh, oh\n" +
                "Oh, oh, oh, ooh, oh, oh\n" +
                "Nobody wanta' see us together\n" +
                "But it don't matter, no ('cause I got you babe)\n" +
                "Nobody wanta' see us together\n" +
                "But it don't matter, no ('cause I got you babe)\n" +
                "Cause we gonna fight, oh yes, we gonna fight (we gonna fight)\n" +
                "Believe we gonna fight (we gonna fight)\n" +
                "Fight for our right to love, yeah (right to love, yeah)\n" +
                "Nobody wants to see us together\n" +
                "But it don't matter, no ('cause I got you)\n" +
                "Nobody wanta' see us together\n" +
                "Nobody thought that we'd last forever\n" +
                "I feel them hoping and praying\n" +
                "Things between us don't get better (better)\n" +
                "Men steady coming after you (you)\n" +
                "Women steady coming after me (me)\n" +
                "Seems like er'body want to go for self and\n" +
                "Don't wanna respect boundary\n" +
                "Telling you all those lies (oh, ooh\n" +
                "Just to get on your side (side)\n" +
                "But I must admit there was a couple\n" +
                "Of secrets I held inside (inside)\n" +
                "But just know that I try (try)\n" +
                "To always apologize (apologize)\n" +
                "And I'm gonna have you first\n" +
                "Always in my heart to keep you satisfied (satisfied)\n" +
                "Nobody wanta' see us together\n" +
                "But it don't matter, no ('cause I got you babe)\n" +
                "Nobody wanta' see us together\n" +
                "But it don't matter, no ('cause I got you babe)\n" +
                "Cause we gonna fight, oh yes, we gonna fight (we gonna fight)\n" +
                "Believe we gonna fight (we gonna fight)\n" +
                "Fight for our right to love, yeah (right to love, yeah)\n" +
                "Nobody wants to see us together\n" +
                "But it don't matter, no ('cause I got you)\n" +
                "You got every right to want to leave (wanna leave)\n" +
                "You got every right to want to go (wanna go)\n" +
                "You got every right to hit the road (hit the road)\n" +
                "And never talk to me no more (me no more)\n" +
                "You don't even have to call (have to call)\n" +
                "Or even check for me at all (me at all)\n" +
                "Because the way I've been acting lately (I've been acting)\n" +
                "Has been off the wall (off the wall)\n" +
                "Especially towards you (towards you)\n" +
                "Putting girls before you (before you)\n" +
                "And they've been watching everything I've been doing\n" +
                "Just to hurt you (hurt you)\n" +
                "Most of it just ain't true (ain't true)\n" +
                "And they won't show you (show you)\n" +
                "How much of a queen you are to me\n" +
                "And why I love you, baby\n" +
                "Nobody wanta' see us together\n" +
                "But it don't matter, no ('cause I got you babe)\n" +
                "Nobody wanta' see us together\n" +
                "But it don't matter, no ('cause I got you babe)\n" +
                "Cause we gonna fight, oh yes, we gonna fight (we gonna fight)\n" +
                "Believe we gonna fight (we gonna fight)\n" +
                "Fight for our right to love, yeah (right to love, yeah)\n" +
                "Nobody wants to see us together\n" +
                "But it don't matter, no ('cause I got you)\n" +
                "Cause I got you babe\n" +
                "(Ooh, oh, ooh, oh, ooh) Cause I got you babe\n" +
                "(Oh, oh, ooh, oh) Cause I got you babe\n" +
                "(Oh, ooh, oh, ooh) Cause I got you babe\n" +
                "Nobody wanta' see us together\n" +
                "But it don't matter, no ('cause I got you babe)\n" +
                "Nobody wanta' see us together\n" +
                "But it don't matter, no ('cause I got you babe)\n" +
                "Cause we gonna fight, oh yes, we gonna fight (we gonna fight)\n" +
                "Believe we gonna fight (we gonna fight)\n" +
                "Fight for our right to love, yeah (right to love, yeah)\n" +
                "Nobody wants to see us together\n" +
                "But it don't matter, no ('cause I got you)\n" +
                "Nobody wants to see us together\n" +
                "But it don't matter, no ('cause I got you)\n" +
                "Nobody wants to see us together\n" +
                "But it don't matter, no ('cause I got you)\n" +
                "Cause we gonna fight, oh yes, we gonna fight (we gonna fight)\n" +
                "Believe we gonna fight (we gonna fight)\n" +
                "Fight for our right to love, yeah (right to love, yeah)\n" +
                "Nobody wants to see us together\n" +
                "But it don't matter, no ('cause I got you)"));
        arrayList.add(new Song("Beyonce", "-Formation", R.raw.beyonce_formation, R.drawable.beyonce, "Y'all haters corny with that illuminati mess\n" +
                "Paparazzi, catch my fly, and my cocky fresh\n" +
                "I'm so reckless when I rock my Givenchy dress (stylin')\n" +
                "I'm so possessive so I rock his Roc necklaces\n" +
                "My daddy Alabama, momma Louisiana\n" +
                "You mix that negro with that Creole make a Texas bamma\n" +
                "I like my baby hair, with baby hair and afros\n" +
                "I like my negro nose with Jackson Five nostrils\n" +
                "Earned all this money but they never take the country out me\n" +
                "I got a hot sauce in my bag, swag\n" +
                "I see it, I want it\n" +
                "I stunt, yellow bone it\n" +
                "I dream it, I work hard\n" +
                "I grind 'til I own it\n" +
                "I twirl on them haters\n" +
                "Albino alligators\n" +
                "El Camino with the seat low\n" +
                "Sippin' Cuervo with no chaser\n" +
                "Sometimes I go off, I go off\n" +
                "I go hard, I go hard\n" +
                "Get what's mine, take what's mine\n" +
                "I'm a star, I'm a star\n" +
                "'Cause I slay, slay\n" +
                "I slay, hey, I slay, okay\n" +
                "I slay, okay, all day, okay\n" +
                "I slay, okay, I slay okay\n" +
                "We gon' slay, slay\n" +
                "Gon' slay, okay\n" +
                "We slay, okay\n" +
                "I slay, okay\n" +
                "I slay, okay\n" +
                "Okay, okay, I slay, okay\n" +
                "Okay, okay, okay, okay\n" +
                "Okay, okay, ladies, now let's get in formation, 'cause I slay\n" +
                "Okay ladies, now let's get in formation, 'cause I slay\n" +
                "Prove to me you got some coordination, 'cause I slay\n" +
                "Slay trick, or you get eliminated\n" +
                "When he fuck me good I take his ass to Red Lobster, 'cause I slay\n" +
                "When he fuck me good I take his ass to Red Lobster, 'cause I slay\n" +
                "If he hit it right, I might take him on a flight on my chopper, 'cause I slay\n" +
                "Drop him off at the mall, let him buy some J's, let him shop up, 'cause I slay\n" +
                "I might get your getSong played on the radio station, 'cause I slay\n" +
                "I might get your getSong played on the radio station, 'cause I slay\n" +
                "You just might be a black Bill Gates in the making, 'cause I slay\n" +
                "I just might be a black Bill Gates in the making\n" +
                "I see it, I want it\n" +
                "I stunt, yellow bone it\n" +
                "I dream it, I work hard\n" +
                "I grind 'til I own it\n" +
                "I twirl on them haters\n" +
                "Albino alligators\n" +
                "El Camino with the seat low\n" +
                "Sippin' Cuervo with no chaser\n" +
                "Sometimes I go off, I go off\n" +
                "I go hard, I go hard\n" +
                "Take what's mine, take what's mine\n" +
                "I'm a star, I'm a star\n" +
                "'Cause I slay, slay\n" +
                "I slay, hey, I slay, okay\n" +
                "I slay, okay, I slay, okay\n" +
                "I slay, okay, I slay okay\n" +
                "I slay, okay\n" +
                "We gon' slay, slay\n" +
                "Gon' slay, okay\n" +
                "We slay, okay\n" +
                "I slay, okay\n" +
                "I slay, okay\n" +
                "Okay, okay, I slay, okay\n" +
                "Okay, okay, okay, okay\n" +
                "Okay, okay, ladies, now let's get in formation, I slay\n" +
                "Okay ladies, now let's get in formation, I slay\n" +
                "Prove to me you got some coordination, I slay\n" +
                "Slay trick, or you get eliminated, I slay\n" +
                "Okay ladies, now let's get in formation, I slay\n" +
                "Okay ladies, now let's get in formation\n" +
                "You know you that bitch when you cause all this conversation\n" +
                "Always stay gracious, best revenge is your paper"));
        arrayList.add(new Song("Chris Brown", "-Hope You Do", R.raw.chrisbrown_hopeyoudo, R.drawable.chrisbrown, "Yeah\n" +
                "Oh baby\n" +
                "Now the word around town, 'round town that boy goin' crazy (goin' crazy)\n" +
                "It's been a while since I been out, then come back to whip that Mercedes (that Mercedes)\n" +
                "And my wrist keep drippin', grain grippin', I'm doin' 180 (doin' 180)\n" +
                "Now I used to be pimpin', be pimpin' but how can I say this? (But how can I say this?)\n" +
                "Girl, I'm tryna fuck with you (girl, I'm tryna fuck with you)\n" +
                "Girl, don't take no pictures, don't take no pictures (girl, pictures)\n" +
                "Girl, sorry if I'm slurring my words, I've been drinking (girl, been drinking), \n" +
                "Mixin' that liquor (mixin' that liquor)\n" +
                "I rolled up when I woke up (when I woke up)\n" +
                "I brought more if you want some (if you want some)\n" +
                "You say you don't really give a fuck (give a fuck)\n" +
                "I really hope that you know what's up\n" +
                "I ain't playin', oh no (ain't playin'), I ain't playin', no woah\n" +
                "When my mind's fucked up, I just don't care at all \n" +
                "I just hope you pick up when it's two or three \n" +
                "Girl, I'm way too drunk, I just don't care at all \n" +
                "I just hope you pick up when it's two or three\n" +
                "And I hope you do\n" +
                "Do\n" +
                "Do\n" +
                "Do\n" +
                "Do\n" +
                "Do\n" +
                "Do\n" +
                "Do\n" +
                "Need some more liquor, yeah\n" +
                "Eatin' that pussy, I drown in your river, yeah\n" +
                "Oh yeah, oh yeah, I'm not no swimmer\n" +
                "But I would go deep in that ocean like Flipper, oh yeah, oh yeah\n" +
                "When my mind's fucked up, I just don't care at all\n" +
                "I just hope you pick up when it's two or three \n" +
                "Girl, I'm way too drunk, I just don't care at all\n" +
                "I just hope you pick up when it's two or three\n" +
                "And I hope you, yeah\n" +
                "Do (you do)\n" +
                "Do (oh na)\n" +
                "Do (do-do-do-do, yeah)\n" +
                "Do\n" +
                "Do (you do, do ah)\n" +
                "Do\n" +
                "Do (yeah, yeah)\n" +
                "Do (And I hope you do)\n" +
                "Do (do, ah)\n" +
                "Do (doo)\n" +
                "Do \n" +
                "Do (oh baby)\n" +
                "Do (do)\n" +
                "Do (do)\n" +
                "Do (do)\n" +
                "Do (do)\n" +
                "She got the keys to my house and my heart, I hope that she save me (that she save me)\n" +
                "And she know I'm gon' lay the pipe, pipe down, I turn that bitch crazy\n" +
                "Why you trippin'? You won't even listen 'cause you used to all the niggas\n" +
                "I promise I'm different, different\n" +
                "And I hope you take off your clothes, know I'm persistent \n" +
                "I know that that ass is soft \n" +
                "You got the glow, don't care 'bout them sheets, girl, \n" +
                "Let's come take them covers off \n" +
                "When we making love, girl, I just might take the rubber off (take the rubber off)\n" +
                "And if you fuck them niggas and cheat on your nigga, then what you gon' do to me?\n" +
                "Girl, I hope you, huh\n" +
                "Girl, don't listen to them bitches, oh\n" +
                "Girl, don't tell your business, don't tell your business\n" +
                "And girl, just tell me the truth, I'm fucking with you\n" +
                "And I hope you\n" +
                "Do\n" +
                "Do\n" +
                "Do\n" +
                "Do\n" +
                "Do\n" +
                "Do\n" +
                "Do\n" +
                "Do\n" +
                "Need some more liquor, yeah\n" +
                "Eatin' that pussy, I drown in your river"));
        arrayList.add(new Song("Akon ft. Colby'O'Donis", "-Beautiful", R.raw.akon_beautiful_ft_colbyodonis_kardinaloffishall, R.drawable.akon, "When I see you\n" +
                "I run out of words to say (oh oh)\n" +
                "I wouldn't leave you\n" +
                "'Cause you're that type of girl to make me stay (oh oh)\n" +
                "I see the guys tryna' holla\n" +
                "Girl I don't wanna bother you\n" +
                "'Cause you're independent and you got my attention\n" +
                "Can I be your baby father\n" +
                "Girl I just wanna show you\n" +
                "That I love what you are doin' hun\n" +
                "I see you in the club, you gettin' down good\n" +
                "I wanna get with you, yeah\n" +
                "I see you in the club, you showin' thugs love\n" +
                "I wanna get with you\n" +
                "You're so beautiful, so damn beautiful\n" +
                "Said you're so beautiful, so damn beautiful\n" +
                "You're so beautiful, beautiful, beautiful, beautiful\n" +
                "You're so beautiful, beautiful, beautiful, beautiful\n" +
                "You're so beautiful\n" +
                "Like the clouds you\n" +
                "Drift me away, far away (yeah)\n" +
                "And like the sun you\n" +
                "Brighten my day, you brighten my day (yeah)\n" +
                "I never wanna see you cry cry cry\n" +
                "And I never wanna tell a lie lie lie\n" +
                "Said I never wanna see you cry cry cry\n" +
                "And I never wanna tell a lie lie lie\n" +
                "I see you in the club\n" +
                "You gettin' down good\n" +
                "I wanna get with you, yeah\n" +
                "I see you in the club\n" +
                "You showin' thugs love\n" +
                "I wanna get with you\n" +
                "You're so beautiful, so damn beautiful\n" +
                "Said you're so beautiful, so damn beautiful\n" +
                "You're so beautiful, beautiful, beautiful, beautiful\n" +
                "You're so beautiful, beautiful, beautiful, beautiful\n" +
                "Kardinal told you\n" +
                "Whether the sky blue or yellow\n" +
                "This fella ain't that mellow\n" +
                "If it ain't about you (you)\n" +
                "Hourglass shape make the place go (ooh)\n" +
                "Waistline makes my soldier salute\n" +
                "I'ma brute (brute)\n" +
                "High from your high heel game\n" +
                "High heels push up ya ass last name\n" +
                "And you livin' in the fast lane\n" +
                "Eyes like an angel (goddess)\n" +
                "Watch my yellin' as she undress\n" +
                "Spotless (otless) bad to the bone\n" +
                "Make me wanna go put me in the triple X zone (zone)\n" +
                "Lames don't know how to talk to you\n" +
                "So let me walk with you, hold my hand\n" +
                "I'mma spend them grands, but after you undress\n" +
                "Not like a hooker, but more like a princess\n" +
                "Queen, empress, president\n" +
                "Pull any way ya got my love\n" +
                "'Cause your beautiful (okay?)\n" +
                "I see you in the club\n" +
                "You gettin' down good\n" +
                "I wanna get with you (oh yeah)\n" +
                "I see you in the club\n" +
                "You showin' thugs love\n" +
                "I wanna get with you\n" +
                "You're so beautiful, so damn beautiful\n" +
                "Said you're so beautiful, so damn beautiful\n" +
                "You're so beautiful, beautiful, beautiful, beautiful\n" +
                "You're so beautiful, beautiful, beautiful, beautiful\n" +
                "You're so beautiful\n" +
                "Where'd you come from you're outta this world\n" +
                "To me (oh oh)\n" +
                "You're a symbol of what a big beautiful woman should be (ooh wee) (oh oh)\n" +
                "I never wanna see you cry cry cry (don't cry)\n" +
                "And I never wanna tell a lie lie lie (oh yeah)\n" +
                "Said I never wanna see you cry cry cry (oh)\n" +
                "And I never wanna tell a lie lie lie (lie)\n" +
                "I see you in the club, you gettin' down good\n" +
                "I wanna get with you (ooh yeah)\n" +
                "I see you in the club, you showin' thugs love\n" +
                "I wanna get with you\n" +
                "You're so beautiful, so damn beautiful, said you're so beautiful\n" +
                "So damn beautiful, you're so beautiful"));
        arrayList.add(new Song("Akon", "-Locked Up", R.raw.akon_lockedup_ft_stylesp, R.drawable.akon, "I'm steady tryna find a motive,\n" +
                "Why do what I do?,\n" +
                "Freedom ain't gettin' no closer,\n" +
                "No matter how far I go,\n" +
                "My car is stolen, no registration,\n" +
                "Cops patrollin', and now they done stop me,\n" +
                "And I get locked up,\n" +
                "They won't let me out, they won't let me out, (I'm locked up)\n" +
                "They won't let me out no, they won't let me out, (I'm locked up)\n" +
                "They won't let me out, they won't let me out, (I'm locked up)\n" +
                "They won't let me out no, they won't let me out\n" +
                "Headin' up town to re-up\n" +
                "Back with a couple of keys\n" +
                "Corner blocks on fire,\n" +
                "Under covers dressed as fiends,\n" +
                "Makin' so much money,\n" +
                "Ride up smooth and fast,\n" +
                "Put away the stash,\n" +
                "And as I sold the last bag fucked around and got locked up\n" +
                "They won't let me out, they won't let me out, (I'm locked up)\n" +
                "They won't let me out no, they won't let me out, (I'm locked up)\n" +
                "They won't let me out, they won't let me out, (I'm locked up)\n" +
                "They won't let me out no, they won't let me out\n" +
                "'Cause visitation no longer comes by,\n" +
                "Seems like they forgot about me\n" +
                "Commissary is gettin' empty\n" +
                "My cell mates gettin' food wit out me\n" +
                "Can't wait to get out and move forward with my life,\n" +
                "Got a family that loves me and wants me to do right\n" +
                "But instead I'm here locked up\n" +
                "They won't let me out, they won't let me out, (I'm locked up)\n" +
                "They won't let me out no, they won't let me out, (I'm locked up)\n" +
                "They won't let me out, they won't let me out, (I'm locked up)\n" +
                "They won't let me out no, they won't let me out\n" +
                "Maybe a visit (they won't let me out)\n" +
                "Send me some magazines (they won't let me out)\n" +
                "Send me some money orders (they won't let me out, no)\n" +
                "Maybe a visit baby (they won't let me out)\n" +
                "'cause I'm locked up, they won't let me out.\n" +
                "Where's my lawyer? (they won't let me out)\n" +
                "I'm locked up, they won't let me out, no.\n" +
                "Get me outta here (they won't let me out)\n" +
                "I'm locked up, they won't let me out, they won't let me out.\n" +
                "Baby I'm locked up they won't let me out, no\n" +
                "Where's my niggaz?\n" +
                "On the lock-down.\n" +
                "Damn, I'm locked up, they won't let me out.\n" +
                "I'm locked up, they won't let me out.\n" +
                "Oh they won't let me out.\n" +
                "Can you please accept my phone calls?\n" +
                "'Cause I'm locked up, locked up, locked up."));
        arrayList.add(new Song("Ava Max", "-Sweet but Psycho", R.raw.avamax_sweetbutpsycho, R.drawable.avamax, "[Chorus]\n" +
                "Oh, she's sweet but a psycho\n" +
                "A little bit psycho\n" +
                "At night she's screamin'\n" +
                "\"I'm-ma-ma-ma out my mind\"\n" +
                "Oh, she's hot but a psycho\n" +
                "So left but she's right though\n" +
                "At night she's screamin'\n" +
                "\"I'm-ma-ma-ma out my mind\"\n" +
                "\n" +
                "[Verse 1]\n" +
                "She'll make you curse, but she a blessing\n" +
                "She'll rip your shirt within a second\n" +
                "You'll be coming back, back for seconds\n" +
                "With your plate, you just can't help it\n" +
                "\n" +
                "[Pre-Chorus]\n" +
                "No, no\n" +
                "You'll play alo-o-ong\n" +
                "Let her lead you o-o-on\n" +
                "You'll be saying \"No, no\"\n" +
                "Then saying \"Yes, yes, yes\"\n" +
                "'Cause she messin' with your head\n" +
                "\n" +
                "[Chorus]\n" +
                "Oh, she's sweet but a psycho\n" +
                "A little bit psycho\n" +
                "At night she's screamin'\n" +
                "\"I'm-ma-ma-ma out my mind\"\n" +
                "Oh, she's hot but a psycho\n" +
                "So left but she's right though\n" +
                "At night she's screamin'\n" +
                "\"I'm-ma-ma-ma out my mind\"\n" +
                "\n" +
                "[Post-Chorus]\n" +
                "Grab a cop gun kinda crazy\n" +
                "She's poison but tasty\n" +
                "Yeah, people say \"Run, don't walk away\"\n" +
                "'Cause she's sweet but a psycho\n" +
                "A little bit psycho\n" +
                "At night she screamin'\n" +
                "\"I'm-ma-ma-ma out my mind\"\n" +
                "\n" +
                "[Verse 2]\n" +
                "See, someone said, don't drink her potions\n" +
                "She'll kiss your neck with no emotion\n" +
                "When she's mean, you know you love it\n" +
                "She tastes so sweet, don't sugar coat it\n" +
                "\n" +
                "[Pre-Chorus]\n" +
                "No, no\n" +
                "You'll play alo-o-ong\n" +
                "Let her lead you o-o-on\n" +
                "You'll be saying \"No (no, no, no), no (no)\"\n" +
                "Then saying \"Yes, yes, yes\"\n" +
                "'Cause she messin' with your head\n" +
                "\n" +
                "[Chorus]\n" +
                "Oh, she's sweet but a psycho\n" +
                "A little bit psycho\n" +
                "At night she's screamin'\n" +
                "\"I'm-ma-ma-ma out my mind\"\n" +
                "Oh, she's hot but a psycho\n" +
                "So left but she's right though\n" +
                "At night she's screamin'\n" +
                "\"I'm-ma-ma-ma out my mind\"\n" +
                "\n" +
                "[Post-Chorus]\n" +
                "Grab a cop gun kinda crazy\n" +
                "She's poison but tasty\n" +
                "Yeah, people say \"Run, don't walk away\"\n" +
                "'Cause she's sweet but a psycho\n" +
                "A little bit psycho\n" +
                "At night she's screamin'\n" +
                "\"I'm-ma-ma-ma out my mind\"\n" +
                "\n" +
                "[Bridge]\n" +
                "You're just like me, you're out your mind\n" +
                "I know it's strange, we're both the crazy kind\n" +
                "You're tellin' me that I'm insane\n" +
                "Boy, don't pretend that you don't love the pain\n" +
                "\n" +
                "[Chorus]\n" +
                "Oh, she's sweet but a psycho\n" +
                "A little bit psycho\n" +
                "At night she's screamin'\n" +
                "\"I'm-ma-ma-ma out my mind\"\n" +
                "Oh, she's hot but a psycho\n" +
                "So left but she's right though\n" +
                "At night she's screamin'\n" +
                "\"I'm-ma-ma-ma out my mind\"\n" +
                "\n" +
                "[Post-Chorus]\n" +
                "Grab a cop gun kinda crazy\n" +
                "She's poison but tasty\n" +
                "Yeah, people say \"Run, don't walk away\"\n" +
                "'Cause she's sweet but a psycho\n" +
                "A little bit psycho\n" +
                "At night she's screamin'\n" +
                "\"I'm-ma-ma-ma out my mind\""));
        //endregion

        //Create a new adapter of my custom adapter and assign its values

        adapter = new ListViewAdapter(R.layout.songlist_customlayout, arrayList, getActivity());

        //Set my listview to my custom adapter
        songListView.setAdapter(adapter);

        //Click on a specific getSong from my list
        songListView.setOnItemClickListener(new ListViewClickListener());


        //return my view
        return view;
    }

    //I call this class inside my onCreateView so i dont populate it too much
    private class ListViewClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            //Initiliaze my songFragment to my fragment class
            songFragment = new SongFragment();

            //get details of the getSong clicked on to the SongFragment page
            final Song song = arrayList.get(position);
            songFragment.getSongDetails(song.getArtist(), song.getSongName(), song.getArtistImg(), song.getSong(), song.getLyrics());

            //call FragmentManager and begin the transaction to my songFragment class
            fragmentManager = getFragmentManager();
            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.pull_up, R.anim.pull_down);

            //When clicked on a listview item - navigate to songfragment and when clicked back -> go back to mainfragment
            //save my mainfragment to my stack so it isnt destroyed but kept safe so i can get back to it
            fragmentTransaction.replace(R.id.fragment_container, songFragment).addToBackStack(null).commit();

        }
    }

    //region Unused Method - Replaced with method underneath
    //    //Stop getSong when fragment is changed
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        //Call method onStop from my adapter
//        adapter.StopSong();
//    }
    //endregion

    @Override
    public void onPause() {
        super.onPause();

        //Call method PauseSong inside my adapter
        adapter.PauseSong();
    }




}
