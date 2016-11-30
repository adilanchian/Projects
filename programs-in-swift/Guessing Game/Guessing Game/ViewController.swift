//
//  ViewController.swift
//  Guessing Game
//
//  Created by Alec Dilanchian on 8/10/15.
//  Copyright © 2015 Alec Dilanchian. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    @IBOutlet weak var GuessBox: UITextField!
    @IBOutlet weak var PrintLabel: UILabel!
    
    @IBAction func SubmitButton(_ sender: AnyObject) {
        
        let computerNumber = String(arc4random_uniform(6));
       
        if computerNumber == GuessBox.text{
            PrintLabel.text = "You guessed right!";
            GuessBox.text = "";
            
            
        } else if computerNumber != GuessBox.text{
            PrintLabel.text = "Wrong! The number was \(computerNumber)";
            GuessBox.text = "";
            }
        }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
//        if PrintLabel.text == "You guessed right!"{
//            button.hidden = false;
//        }
        
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

