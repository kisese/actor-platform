//
//  AppDelegate.swift
//  ActorClient
//
//  Created by Stepan Korshakov on 10.03.15.
//  Copyright (c) 2015 Actor LLC. All rights reserved.
//

import Foundation

@objc class AppDelegate : UIResponder,  UIApplicationDelegate {
    
    // MARK: -
    // MARK: Private vars
    
    private var window : UIWindow?;
    private var binder = Binder()
    
    // MARK: -
    
    func application(application: UIApplication, didFinishLaunchingWithOptions launchOptions: [NSObject : AnyObject]?) -> Bool {

        MainAppTheme.navigation.applyAppearance(application)
        MainAppTheme.tab.applyAppearance(application)
        MainAppTheme.search.applyAppearance(application)
        
//        var textFieldAppearance = UITextField.appearance();
//        textFieldAppearance.tintColor = Resources.TintColor;
        
        //var searchBarAppearance = UISearchBar.appearance();
        //searchBarAppearance.tintColor = Resources.TintColor;
        

//         273c52
        
//        UITabBar.appearance().translucent = false
//        UITabBar.appearance().tintColor = Resources.BarTintColor;
////        UITabBar.appearance().backgroundImage = Imaging.imageWithColor(UIColor.whiteColor(), size: CGSize(width: 1, height: 46))
//        UITabBar.appearance().shadowImage = UIImage(named: "CardTop2");
//        UITabBarItem.appearance().setTitleTextAttributes([NSForegroundColorAttributeName: Resources.BarTintUnselectedColor], forState: UIControlState.Normal)
//        [UITabBarItem.appearance setTitleTextAttributes:
//        @{NSForegroundColorAttributeName : [UIColor greenColor]}
//        forState:UIControlStateNormal];
        
//        UITabBar.appearance().selectionIndicatorImage = Imaging.imageWithColor(UIColor(red: 0xeb/255.0, green: 0xed/255.0, blue: 0xf2/255.0, alpha: 1), size: CGSize(width: 1, height: 46)).resizableImageWithCapInsets(UIEdgeInsetsZero);
        
        //        setTitleTextAttributes(NSForegroundColorAttributeName, );
        
        //        var barButtonItemAppearance = UIBarButtonItem.appearance();
        //        barButtonItemAppearance
        
        //        [UINavigationBar appearance].tintColor = [UIColor whiteColor];
        //        [UINavigationBar appearance].barTintColor = BAR_COLOR;
        //        [UINavigationBar appearance].backgroundColor = BAR_COLOR;
        //        [UINavigationBar appearance].titleTextAttributes = @{NSForegroundColorAttributeName:[UIColor whiteColor]};
        //        [UITextField appearance].tintColor = BAR_COLOR;
        //        [UISearchBar appearance].tintColor = BAR_COLOR;
        //        //[UISearchBar appearance].backgroundImage = [UIImage new];
        //        [[UIBarButtonItem appearanceWhenContainedIn:[UISearchBar class],nil]
        //        setTitleTextAttributes:@{NSForegroundColorAttributeName:[UIColor whiteColor]} forState:UIControlStateNormal];
        //
        //        [UITableViewCell appearance].tintColor = BAR_COLOR;
        //        [UITableView appearance].sectionIndexColor = BAR_COLOR;
        //        [UITabBar appearance].tintColor = BAR_COLOR;
        //        
        //        [MagicalRecord setupAutoMigratingCoreDataStack];
        //        
        //        [CocoaMessenger messenger];

        
        var rootController = MainTabController()
        
        var splitController = UISplitViewController()
        splitController.viewControllers = [rootController, NoSelectionController()]
//        splitController.addChildViewController(rootController)
//        splitController.addChildViewController(UIViewController())
    
        window = UIWindow(frame: UIScreen.mainScreen().bounds);
        window?.rootViewController = splitController;
        window?.makeKeyAndVisible();
        window?.backgroundColor = UIColor.whiteColor()
        
        if (MSG.isLoggedIn() == false) {
            let phoneController = AAAuthPhoneController()
            var loginNavigation = AANavigationController(rootViewController: phoneController)
            loginNavigation.navigationBar.tintColor = Resources.BarTintColor
            loginNavigation.makeBarTransparent()
            rootController.presentViewController(loginNavigation, animated: false, completion: nil)
        } else {
            if application.respondsToSelector("registerUserNotificationSettings:") {
                let types: UIUserNotificationType = (.Alert | .Badge | .Sound)
                let settings: UIUserNotificationSettings = UIUserNotificationSettings(forTypes: types, categories: nil)
                application.registerUserNotificationSettings(settings)
                application.registerForRemoteNotifications()
            } else {
                application.registerForRemoteNotificationTypes(.Alert | .Badge | .Sound)
            }
        }
        
        if let hockey = NSBundle.mainBundle().infoDictionary?["HOCKEY"] as? String {
            if (hockey.trim().size() > 0) {
                BITHockeyManager.sharedHockeyManager().configureWithIdentifier(hockey)
                BITHockeyManager.sharedHockeyManager().updateManager.checkForUpdateOnLaunch = true
                BITHockeyManager.sharedHockeyManager().startManager()
                BITHockeyManager.sharedHockeyManager().authenticator.authenticateInstallation()
            }
        }
        
        binder.bind(MSG.getAppState().getIsAppLoaded(), valueModel2: MSG.getAppState().getIsAppEmpty()) { (loaded: JavaLangBoolean?, empty: JavaLangBoolean?) -> () in
            if (empty!.booleanValue()) {
                if (loaded!.booleanValue()) {
                    rootController.showAppIsEmptyPlaceholder()
                } else {
                    rootController.showAppIsSyncingPlaceholder()
                }
            } else {
                rootController.hidePlaceholders()
            }
        }
        
//        binder.bind(MSG.getAppState().getIsAppLoaded(), closure: { (value: Any?) -> () in
//            if let loaded = value as? JavaLangBoolean {
//                if Bool(loaded.booleanValue()) == true {
//                    rootController.hideAppIsSyncingPlaceholder()
//                } else {
//                    rootController.showAppIsSyncingPlaceholder()
//                }
//            }
//        })
        
        return true;
    }
    
    func applicationWillEnterForeground(application: UIApplication) {
        MSG.onAppVisible();
    }

    func applicationDidEnterBackground(application: UIApplication) {
        MSG.onAppHidden();
    }
    
    // MARK: -
    // MARK: Notifications
    
    func application(application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: NSData) {
        let tokenString = "\(deviceToken)".stringByReplacingOccurrencesOfString(" ", withString: "").stringByReplacingOccurrencesOfString("<", withString: "").stringByReplacingOccurrencesOfString(">", withString: "")
        MSG.registerApplePushWithInt(jint((NSBundle.mainBundle().objectForInfoDictionaryKey("API_PUSH_ID") as! String).toInt()!), withNSString: tokenString)
    }
    
    func application(application: UIApplication, didReceiveRemoteNotification userInfo: [NSObject : AnyObject]) {
        println("\(userInfo)")
    }
    
}