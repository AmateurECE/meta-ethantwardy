require ["fileinto", "mailbox"];

if header :contains "X-Spam" "Yes" {
  fileinto :create "Spam";
}
