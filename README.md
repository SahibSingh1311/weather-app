Created a weather App that uses retrofit for api calling, follows MVVM architecture pattern, used co routines and Live Data. 

Difficulties faced:
1 Not able to fetch the current location coordinates using Mobiles GPS.
  - Studied about FusedLocation and the required permissions.

2 The API call was stuck at Laoding
  - Back tracked the Live Data that was used to check the sate of the api call
  - Found that Live Data was not correctly handled

3 Infinite spontaneous API calls
  - Was not handling coroutines

4 Adding RoomDB dependencies
  - There was issue with the versions I was using for ROOMDB

Used Logo and Images from 
- https://www.vecteezy.com/vector-art/7819333-vector-3d-realistic-design-icon-cute-cloud-cartoon-style-collection-set
- https://www.flaticon.com/
