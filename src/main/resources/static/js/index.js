function validate() {
	var screenName = document.getElementById('screenName').value;
	if (!screenName == "") {
		localStorage["screenName"] = screenName;
		return true;
	}
	return false;
}