package tz.co.asoft

interface ISystemPermission {
    val title: String
    val details: String
    val needs: List<String>
}
