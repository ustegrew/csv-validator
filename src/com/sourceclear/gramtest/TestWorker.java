package com.sourceclear.gramtest;

import org.antlr.v4.runtime.*;

import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicInteger;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 *
 */
public class TestWorker implements Runnable
{
    private AtomicInteger         fDoStop;
    private ParserRuleContext     fTree;
    private GeneratorVisitor      fExtractor;
    private int                   fDepth = 2;
    private int                   fMax   = 4;
    private int                   fMin   = 1;
    private BlockingQueue<String> fQueue;

    public TestWorker (InputStream bnfGrammar, BlockingQueue<String> queue) throws IOException
    {
        fDoStop                     = new AtomicInteger (0);
        Lexer lexer                 = new bnfLexer (new ANTLRInputStream (bnfGrammar));
        CommonTokenStream tokens    = new CommonTokenStream (lexer);
        bnfParser grammarparser     = new bnfParser (tokens);
        this.fTree                  = grammarparser.rulelist ();
        this.fQueue                 = queue;
    }

    public TestWorker (InputStream bnfGrammar, BlockingQueue<String> queue, int depth, int min, int max)
            throws IOException
    {
        fDoStop                     = new AtomicInteger (0);
        Lexer lexer                 = new bnfLexer (new ANTLRInputStream (bnfGrammar));
        CommonTokenStream tokens    = new CommonTokenStream (lexer);
        bnfParser grammarparser     = new bnfParser (tokens);
        this.fTree                  = grammarparser.rulelist ();
        this.fQueue                 = queue;
        this.fDepth                 = depth;
        this.fMax                   = max;
        this.fMin                   = min;
    }
    
    public void terminate ()
    {
        fDoStop.getAndSet (1);
    }
    
    public boolean hasTerminated ()
    {
        int     doStop;
        boolean ret;
        
        doStop = fDoStop.getAndAdd (0);
        ret    = (doStop >= 1);
        
        return ret;
    }

    public void run ()
    {
        int         doStop;
        
        do
        {
            this.fExtractor = new GeneratorVisitor (fDepth * fMax, fDepth, fMin, fMax, true);
            this.fExtractor.visit (fTree);
            List<String> generatedTests = this.fExtractor.getTests ();
            
            for (String s : generatedTests)
            {
                try
                {
                    this.fQueue.put (s);
                }
                catch (InterruptedException e) {}
            }
            
            if ( (fDepth + fMax) % 2 == 0)
            {
                fDepth++;
            }
            else
            {
                fMax++;
            }
            
            doStop = fDoStop.getAndAdd (0);
        }
        while (doStop <= 0);
    }

}
