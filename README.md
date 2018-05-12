# Android web3j example of ethereum wallet creation, connecting ethereum client and transfer funds to another accounts.
First, add the following dependencies to your project(app/build.gradle):

implementation 'org.web3j:core:3.3.1-android'

Then use WalletUtils to create a new ethereum wallet into your devie

            val walletPath = WalletUtils.generateFullNewWalletFile("yourownpassword", walletPathFile)
