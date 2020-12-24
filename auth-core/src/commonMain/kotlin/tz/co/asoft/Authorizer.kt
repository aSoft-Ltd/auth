package tz.co.asoft

interface Authorizer{
    val allowedUrls: List<String>
    
    /**
     * @param principle the user/client app you are trying to authorize
     */
    suspend fun authorize(principle: Principle): String
}