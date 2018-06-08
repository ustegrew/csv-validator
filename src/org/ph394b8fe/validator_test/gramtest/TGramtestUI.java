/*
 * *************************************************************************
 * Copyright 2018 Peter Hoppe
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.ph394b8fe.validator_test.gramtest;

import java.io.File;

/**
 *
 * @author peter
 */
public class TGramtestUI extends javax.swing.JFrame
{
    private static final long serialVersionUID = 2089492587091743616L;
    
    private javax.swing.JButton                         fBtnBrowse;
    private javax.swing.JButton                         fBtnRunStop;
    private TGramtest                                   fHost;
    private javax.swing.JLabel                          fLblValueMaxLen;
    private javax.swing.JLabel                          fLblValueMinLen;
    private javax.swing.JLabel                          fLblValueRecursionDepth;
    private javax.swing.JSlider                         fSldMaxLength;
    private javax.swing.JSlider                         fSldMinLength;
    private javax.swing.JSlider                         fSldRecursionDepth;
    private javax.swing.JTextField                      fTxtGrammarFilePath;
    private javax.swing.JTextArea                       fTxtOutput;
    private javax.swing.JFileChooser                    jFileChooser;
    private javax.swing.JLabel                          jLabel1;
    private javax.swing.JLabel                          jLabel2;
    private javax.swing.JLabel                          jLabel3;
    private javax.swing.JPanel                          jPanel1;
    private javax.swing.JPanel                          jPanel2;
    private javax.swing.JPanel                          jPanel3;
    private javax.swing.JScrollPane                     jScrollPane1;
    
    private boolean                                     fIsRunning;
    private StringBuilder                               fBuffer;

    /**
     * Creates new form TGramtestUI
     */
    public TGramtestUI (TGramtest host)
    {
        fBuffer         = new StringBuilder ();
        fHost           = host;
        fIsRunning      = false;
        initComponents ();
    }

    public void addEntry (String entry)
    {
        fBuffer.append (entry);
        fBuffer.append ("\n");
        fTxtOutput.setText (fBuffer.toString ());
    }
    
    public void clearEntries ()
    {
        fBuffer.setLength (0);
        fTxtOutput.setText ("");
    }
    
    private void _setTextRunButton ()
    {
        String caption;
        
        caption = fIsRunning ? "Stop" : "Run";
        fBtnRunStop.setText (caption);
    }
    
    private void _BtnBrowseActionPerformed (java.awt.event.ActionEvent evt)
    {
        int     option;
        File    chosenFile;
        String  filePath;
        
        option = jFileChooser.showOpenDialog (null);
        if (option == javax.swing.JFileChooser.APPROVE_OPTION)
        {
            chosenFile = jFileChooser.getSelectedFile ();
            filePath   = chosenFile.getAbsolutePath ();
            fTxtGrammarFilePath.setText (filePath);
            fHost.notifyFileChanged (chosenFile);
        }
    }

    private void _BtnRunStopActionPerformed (java.awt.event.ActionEvent evt)
    {
        fIsRunning = ! fIsRunning;
        
        _setTextRunButton ();
        fHost.notifyChangeRunState ();
    }

    private void _SldMaxLengthStateChanged (javax.swing.event.ChangeEvent evt)
    {
        int value;
        
        value = fSldMaxLength.getValue ();
        fHost.notifyParamChangeMaxLength (value);
        fLblValueMaxLen.setText ("" + value);
    }

    private void _SldMinLengthStateChanged (javax.swing.event.ChangeEvent evt)
    {
        int value;
        
        value = fSldMinLength.getValue ();
        fHost.notifyParamChangeMinLength (value);
        fLblValueMinLen.setText ("" + value);
    }

    private void _SldRecursionDepthStateChanged (javax.swing.event.ChangeEvent evt)
    {
        int value;
        
        value = fSldRecursionDepth.getValue ();
        fHost.notifyParamChangeRecursionDepth (value);
        fLblValueRecursionDepth.setText ("" + value);
    }

    private void initComponents ()
    {
        jPanel1 = new javax.swing.JPanel ();
        fTxtGrammarFilePath = new javax.swing.JTextField ();
        fBtnBrowse = new javax.swing.JButton ();
        jPanel2 = new javax.swing.JPanel ();
        jLabel1 = new javax.swing.JLabel ();
        fSldRecursionDepth = new javax.swing.JSlider ();
        jLabel2 = new javax.swing.JLabel ();
        jLabel3 = new javax.swing.JLabel ();
        fSldMaxLength = new javax.swing.JSlider ();
        fSldMinLength = new javax.swing.JSlider ();
        fLblValueRecursionDepth = new javax.swing.JLabel ();
        fLblValueMinLen = new javax.swing.JLabel ();
        fLblValueMaxLen = new javax.swing.JLabel ();
        jScrollPane1 = new javax.swing.JScrollPane ();
        fTxtOutput = new javax.swing.JTextArea ();
        jPanel3 = new javax.swing.JPanel ();
        fBtnRunStop = new javax.swing.JButton ();
        jFileChooser = new javax.swing.JFileChooser ();

        jFileChooser.setFileSelectionMode (javax.swing.JFileChooser.FILES_ONLY);
        jFileChooser.setDialogTitle ("Choose grammar file");
        jFileChooser.setMultiSelectionEnabled (false);
        
        setDefaultCloseOperation (javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize (new java.awt.Dimension (5000, 5000));
        setMinimumSize (new java.awt.Dimension (800, 800));
        setPreferredSize (new java.awt.Dimension (800, 800));

        jPanel1.setBorder (javax.swing.BorderFactory
                .createTitledBorder (javax.swing.BorderFactory.createEtchedBorder (), "Grammar file"));

        fTxtGrammarFilePath.setEditable (false);
        fTxtGrammarFilePath.setBackground (new java.awt.Color (255, 255, 255));
        fTxtGrammarFilePath.setToolTipText ("Path to grammar file.");

        fBtnBrowse.setText ("...");
        fBtnBrowse.addActionListener (new java.awt.event.ActionListener ()
        {
            public void actionPerformed (java.awt.event.ActionEvent evt)
            {
                _BtnBrowseActionPerformed (evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout (jPanel1);
        jPanel1.setLayout (jPanel1Layout);
        jPanel1Layout.setHorizontalGroup (jPanel1Layout.createParallelGroup (javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup (jPanel1Layout.createSequentialGroup ().addGap (5, 5, 5).addComponent (fTxtGrammarFilePath)
                        .addPreferredGap (javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent (fBtnBrowse)
                        .addContainerGap ()));
        jPanel1Layout.setVerticalGroup (jPanel1Layout.createParallelGroup (javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup (jPanel1Layout.createSequentialGroup ().addGap (0, 12, Short.MAX_VALUE)
                        .addGroup (jPanel1Layout.createParallelGroup (javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent (fTxtGrammarFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 27,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent (fBtnBrowse))
                        .addContainerGap ()));

        jPanel2.setBorder (javax.swing.BorderFactory
                .createTitledBorder (javax.swing.BorderFactory.createEtchedBorder (), "Generator options"));

        jLabel1.setFont (new java.awt.Font ("Dialog", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment (javax.swing.SwingConstants.RIGHT);
        jLabel1.setText ("RecursionDepth");
        jLabel1.setBorder (new javax.swing.border.SoftBevelBorder (0));

        fSldRecursionDepth.setFont (new java.awt.Font ("Dialog", 0, 10)); // NOI18N
        fSldRecursionDepth.setMajorTickSpacing (5);
        fSldRecursionDepth.setMaximum (24);
        fSldRecursionDepth.setMinorTickSpacing (1);
        fSldRecursionDepth.setPaintLabels (true);
        fSldRecursionDepth.setPaintTicks (true);
        fSldRecursionDepth.setPaintTrack (false);
        fSldRecursionDepth.setSnapToTicks (true);
        fSldRecursionDepth.setValue (0);
        fSldRecursionDepth.setBorder (new javax.swing.border.SoftBevelBorder (0));
        fSldRecursionDepth.addChangeListener (new javax.swing.event.ChangeListener ()
        {
            public void stateChanged (javax.swing.event.ChangeEvent evt)
            {
                _SldRecursionDepthStateChanged (evt);
            }
        });

        jLabel2.setFont (new java.awt.Font ("Dialog", 0, 12)); // NOI18N
        jLabel2.setHorizontalAlignment (javax.swing.SwingConstants.RIGHT);
        jLabel2.setText ("MinLen");
        jLabel2.setBorder (new javax.swing.border.SoftBevelBorder (0));

        jLabel3.setFont (new java.awt.Font ("Dialog", 0, 12)); // NOI18N
        jLabel3.setHorizontalAlignment (javax.swing.SwingConstants.RIGHT);
        jLabel3.setText ("MaxLen");
        jLabel3.setBorder (new javax.swing.border.SoftBevelBorder (0));

        fSldMaxLength.setMajorTickSpacing (5);
        fSldMaxLength.setMaximum (50);
        fSldMaxLength.setMinorTickSpacing (1);
        fSldMaxLength.setPaintLabels (true);
        fSldMaxLength.setPaintTicks (true);
        fSldMaxLength.setPaintTrack (false);
        fSldMaxLength.setSnapToTicks (true);
        fSldMaxLength.setValue (0);
        fSldMaxLength.setBorder (new javax.swing.border.SoftBevelBorder (0));
        fSldMaxLength.addChangeListener (new javax.swing.event.ChangeListener ()
        {
            public void stateChanged (javax.swing.event.ChangeEvent evt)
            {
                _SldMaxLengthStateChanged (evt);
            }
        });

        fSldMinLength.setMajorTickSpacing (5);
        fSldMinLength.setMaximum (50);
        fSldMinLength.setMinorTickSpacing (1);
        fSldMinLength.setPaintLabels (true);
        fSldMinLength.setPaintTicks (true);
        fSldMinLength.setPaintTrack (false);
        fSldMinLength.setSnapToTicks (true);
        fSldMinLength.setValue (0);
        fSldMinLength.setBorder (new javax.swing.border.SoftBevelBorder (0));
        fSldMinLength.addChangeListener (new javax.swing.event.ChangeListener ()
        {
            public void stateChanged (javax.swing.event.ChangeEvent evt)
            {
                _SldMinLengthStateChanged (evt);
            }
        });

        fLblValueRecursionDepth.setBorder (javax.swing.BorderFactory.createEtchedBorder ());
        fLblValueRecursionDepth.setHorizontalTextPosition (javax.swing.SwingConstants.CENTER);
        fLblValueRecursionDepth.setMaximumSize (new java.awt.Dimension (100, 5));
        fLblValueRecursionDepth.setMinimumSize (new java.awt.Dimension (100, 5));
        fLblValueRecursionDepth.setPreferredSize (new java.awt.Dimension (100, 5));

        fLblValueMinLen.setBorder (javax.swing.BorderFactory.createEtchedBorder ());

        fLblValueMaxLen.setBorder (javax.swing.BorderFactory.createEtchedBorder ());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout (jPanel2);
        jPanel2.setLayout (jPanel2Layout);
        jPanel2Layout.setHorizontalGroup (jPanel2Layout.createParallelGroup (javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup (jPanel2Layout.createSequentialGroup ().addContainerGap ()
                        .addGroup (jPanel2Layout.createParallelGroup (javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent (jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent (jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent (jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap (1, 1, 1)
                        .addGroup (jPanel2Layout.createParallelGroup (javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent (fSldRecursionDepth, javax.swing.GroupLayout.Alignment.LEADING,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
                                .addComponent (fSldMinLength, javax.swing.GroupLayout.Alignment.LEADING,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)
                                .addComponent (fSldMaxLength, javax.swing.GroupLayout.Alignment.LEADING,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE))
                        .addPreferredGap (javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup (jPanel2Layout.createParallelGroup (javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent (fLblValueRecursionDepth, javax.swing.GroupLayout.Alignment.TRAILING,
                                        javax.swing.GroupLayout.PREFERRED_SIZE, 60,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent (fLblValueMaxLen, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent (fLblValueMinLen, javax.swing.GroupLayout.Alignment.TRAILING,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE))
                        .addContainerGap ()));
        jPanel2Layout.setVerticalGroup (jPanel2Layout.createParallelGroup (javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup (jPanel2Layout.createSequentialGroup ().addContainerGap ()
                        .addGroup (jPanel2Layout.createParallelGroup (javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent (fSldRecursionDepth, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent (jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent (fLblValueRecursionDepth, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap (javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup (jPanel2Layout.createParallelGroup (javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent (fSldMinLength, javax.swing.GroupLayout.Alignment.LEADING,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)
                                .addComponent (jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent (fLblValueMinLen, javax.swing.GroupLayout.Alignment.LEADING,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE))
                        .addGap (6, 6, 6)
                        .addGroup (jPanel2Layout.createParallelGroup (javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent (fSldMaxLength, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent (jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent (fLblValueMaxLen, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap ()));

        jScrollPane1.setHorizontalScrollBarPolicy (javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy (javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        fTxtOutput.setColumns (20);
        fTxtOutput.setRows (5);
        jScrollPane1.setViewportView (fTxtOutput);

        fBtnRunStop.setText ("Run");
        fBtnRunStop.setMaximumSize (new java.awt.Dimension (75, 35));
        fBtnRunStop.setMinimumSize (new java.awt.Dimension (75, 35));
        fBtnRunStop.setPreferredSize (new java.awt.Dimension (75, 35));
        fBtnRunStop.addActionListener (new java.awt.event.ActionListener ()
        {
            public void actionPerformed (java.awt.event.ActionEvent evt)
            {
                _BtnRunStopActionPerformed (evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout (jPanel3);
        jPanel3.setLayout (jPanel3Layout);
        jPanel3Layout
                .setHorizontalGroup (jPanel3Layout.createParallelGroup (javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup (javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup ()
                                .addContainerGap (javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent (fBtnRunStop, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap ()));
        jPanel3Layout.setVerticalGroup (jPanel3Layout.createParallelGroup (javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup (javax.swing.GroupLayout.Alignment.TRAILING,
                        jPanel3Layout.createSequentialGroup ()
                                .addContainerGap (javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent (fBtnRunStop, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap ()));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout (getContentPane ());
        getContentPane ().setLayout (layout);
        layout.setHorizontalGroup (layout.createParallelGroup (javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup (layout.createSequentialGroup ().addGroup (layout
                        .createParallelGroup (javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup (layout.createSequentialGroup ().addGap (12, 12, 12)
                                .addGroup (layout.createParallelGroup (javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent (jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent (jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup (layout.createSequentialGroup ().addContainerGap ().addComponent (jPanel3,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE))
                        .addComponent (jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)).addContainerGap ()));
        layout.setVerticalGroup (layout.createParallelGroup (javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup (layout.createSequentialGroup ().addContainerGap ()
                        .addComponent (jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap (javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent (jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 195,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap (javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent (jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                        .addPreferredGap (javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent (jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap ()));

        pack ();
        
        _setTextRunButton ();
        fLblValueRecursionDepth.setText ("" + fSldRecursionDepth.getValue ());
        fLblValueMaxLen.setText ("" + fSldMaxLength.getValue ());
        fLblValueMinLen.setText ("" + fSldMinLength.getValue ());
        fHost.notifyParamChangeRecursionDepth (fSldRecursionDepth.getValue ());
        fHost.notifyParamChangeMaxLength (fSldMaxLength.getValue ());
        fHost.notifyParamChangeMinLength (fSldMinLength.getValue ());
    }
}
