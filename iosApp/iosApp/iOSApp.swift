import SwiftUI
import ComposeApp
import Firebase

@main
struct iOSApp: App {
    
    init() {
        KoinKt.doInitKoin()
        FirebaseApp.configure()
    }
    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
