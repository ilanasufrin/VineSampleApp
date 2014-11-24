VineSampleApp
=============

### This app hits the Vine API to get all videos from a particular user's timeline. It does this by performing the following tasks:
- Parses and maintains the following API call: https://vine.co/api/timelines/users/918753190470619136
- Create a ListView using the data from the above call, showing the thumbnail and relevant metadata
- Create a Video Detail View that is activated on clicking the individual table cell that autoplays the “videoURL" for the item selected (please note that you must be using an Android phone, NOT an emulator, to view the videos)

The app uses the [Picasso library](http://square.github.io/picasso/) to render the thumbnail URLS and VideoView as a wrapper for the MediaPlayer class.

#### Screenshots

ListView:

![](https://github.com/ilanasufrin/VineSampleApp/blob/master/main/screenshots/video_object_listview.png)

DetailView:

<img src="https://github.com/ilanasufrin/VineSampleApp/blob/master/main/screenshots/video_detail_view_phone.png" height="760" width="464" >

##### The [screenshots folder](https://github.com/ilanasufrin/VineSampleApp/tree/master/main/screenshots) shows what the app looked like at virtually every stage of development.


#### Ideas For Future Development
- Make it prettier overall
- Add options for user preferences. Allow the user to input a Vine user ID for the API call
- Add controls for a fancier MediaPlayer
- Add a proper backbutton and menu topbar navigation
