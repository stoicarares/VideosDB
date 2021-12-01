// Stoica Rares-Tiberiu 321CA

        For the begginig of the implementation, I made a Database where I stored all the things given in input
    (Users, Actors, Movies, Serials), but actions. Implemented transfer functions from inputData type to my new classes, 
    based on fields they got to having them in my own Database. I also stored a list of Videos(Movies + Serials). Because
    the checker reopens the program everytime checks one test, when i use my transfer functions, I firstly clear the lists.

        Distribution of classes:

        #Package entertainment.

            *Video  _____ Movie - Store information for a Movie
                    |
                    |
                    |____ Serial - Store information for a Serial
            
            *Database - where I store the information given on input

            *User - Store information for a user


        #Package actor
            *Actor

____________REQUESTS__________________

    Every action builds a StringBuilder which contain the output of the request.

    #Package actions:
        |
        |___.commands
        |            |
        |            |_____Command    - Abstract class which contains the main fields given to this type of actions
        |            |                - Implements some methods that are going to be used by all commands.
        |            |                - Gets the correct instance of its subclasses to solve the right command requested
        |            |
        |            |_____Favorite   - Implements abstract method to add a video on a user's favorite list.
        |            |
        |            |_____Rating     - Implements abstract method to add a rating to a video's rating list.
        |            |
        |            |_____View       - Implements abstract method to add a video to a user's history.
        |
        |
        |___.queries
        |        |
        |        |______Query      - Abstract class without abstract method which contain the main fields given to this
        |        |                       type of action.
        |        |                 - Implements some methods that are going to be used by all queries(EX. Sorting functions)
        |        |______.actors
        |        |         |____QueryActor         - Abstract method with abstract function which will be implemented in the classes below
        |        |         |
        |        |         |____Average            - Implements abstract method to sort the actors by the video's ratings they played in
        |        |         |
        |        |         |____Awards             - Implements abstract method to sort the actors by the awards they got
        |        |         |
        |        |         |____FilterDescription  - Implements abstract method to sort the actors by matching some given words to their
        |        |                                         career description
        |        |           
        |        |_____.users
        |        |         |____QueryUser          - Abstract method with abstract function which will be implemented in the class below
        |        |         |
        |        |         |____NumberOfRatings    - Implements abstract method to sort by the number videos rated
        |        |         
        |        |
        |        |_____.videos
        |                 |____QueryVideo          - Abstract method with abstract function which will be implemented in the classes below
        |                 |
        |                 |_____Rating             - Implements abstract method to sort videos by their ratings
        |                 |
        |                 |_____Favorite           - Implements abstract method to sort videos by their number of appearances in users'sort
        |                 |                                 favorite lists
        |                 |
        |                 |_____Longest            - Implements abstract method to sort videos by their duration
        |                 |
        |                 |_____MostViewed         - Implements abstract method to sort videos by the number of times they have been viewed                
        |                 
        |
        |___.recommendations
                |
                |____Recommendation                - Abstract class with abstract method which will be implemented in the classes below
                |                                  - Stores the main fields given to this type of action   
                |                                  - Implements some methods that are going to be used for one or more of its subclasses
                |
                |____.basic - For all users
                |       |
                |       |______Standard            - Implements abstract method to return the first unseen video of an user from the Database
                |       |
                |       |______BestUnseen          - Implements abstract method to return the highest rated unseen video 
                |                                
                |____.premium - only for premium users                
                        |
                        |______Popular             - Implements abstract method to return the first unseen video from the most viewed genre
                        |
                        |______Favorite            - Implements abstract methods to return the first unseen video which is in the most users's
                        |                               favorite lists.
                        |
                        |______Search              - Implements abstract method to return all unseen videos from a given genre sorted by ratings

    I used the list with all videos on recommendations, only in cases i thought it's completely necesary for not repeatting code, because,
        in my opinion, it is clearer to use movies and serials lists separately.
    
    For sorting elements I used Maps where I stored the information needed amd than comparing their values and keys, depends of the action.


    I documented myself from GeeksforGeeks and StackOverflow on implementation at FilterDescription to get the exact words and at
        Awards to use .stream.reduce to get the total sum of the actor's prizes. I also found something there that helped me to
        think the sorting functions.