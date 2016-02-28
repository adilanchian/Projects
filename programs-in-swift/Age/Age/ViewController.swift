//
//  ViewController.swift
//  Age
//
//  Created by Alec Dilanchian on 8/9/15.
//  Copyright © 2015 Alec Dilanchian. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    @IBOutlet weak var age: UITextField!
    @IBOutlet weak var resultLabel: UILabel!
    
    
    @IBAction func findAge(sender: AnyObject) {
        var ageMath = Int(age.text!)!;
        ageMath = ageMath / 7;
        print("Button Clicked");
        resultLabel.text = ("You are \(ageMath) in dog years");
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

