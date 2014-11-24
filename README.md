VineSampleApp
=============

#### This app hits the Vine API to get all videos from a particular user's timeline. It does this by performing the following tasks:
- Parses and maintains the following API call: https://vine.co/api/timelines/users/918753190470619136
- Create a ListView using the data from the above call, showing the thumbnail and relevant metadata
- Create a Video Detail View that is activated on clicking the individual table cell that autoplays the “videoURL" for the item selected

The app uses the [Picasso library](http://square.github.io/picasso/) to render the thumbnail URLS and VideoView as a wrapper for the MediaPlayer class.



##### Done:
- creates listview
- make API call and render result thumbnail urls
- will display data immediately instead of reloading
- Make each list item clickable, will take you to a list item view
- shows thumbnails in listview (uses Picasso library)
- send video to video detail page
- Autoplay video on video detail page (this only works when viewing on real device, not an emulator)

##### Still to do:
- styles (optional)
- user preferences (optional)
