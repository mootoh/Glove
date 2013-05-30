Parse.Cloud.define("throw", function(request, response) {
  var text = request.params['text'];

  Parse.Push.send({
    channels: ["default"],
    data: {
      alert: text,
      action: "net.mootoh.glove.UPDATE_CLIPBOARD",
      title: "title from Parse"
    }
  }, {
    success: function() {
      // Push was successful
      response.success("Push was successful");
    },
    error: function(error) {
      // Handle error
      response.success("Push has an error: " + error.message);
    }
  });
  // response.success("Hello world!");
});