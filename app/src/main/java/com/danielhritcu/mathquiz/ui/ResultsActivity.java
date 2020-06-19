package com.danielhritcu.mathquiz.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.danielhritcu.mathquiz.QuizApp;
import com.danielhritcu.mathquiz.R;
import com.danielhritcu.mathquiz.databinding.ResultListItemBinding;
import com.danielhritcu.mathquiz.databinding.ResultsActivityBinding;
import com.danielhritcu.mathquiz.models.GameState;
import com.danielhritcu.mathquiz.models.Quiz;

import java.util.List;

public class ResultsActivity extends AppCompatActivity {
    private GameState mGameState;
    private ResultsActivityBinding mBinding;
    private ResultsAdapter mResultsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.results_activity);
        mGameState = ((QuizApp)getApplication()).getGameState();
        mResultsAdapter = new ResultsAdapter(mGameState.getResults().getResultsList());
        mBinding.setGameState( mGameState);
        mBinding.setActivity( this );

        mBinding.resultsList.setAdapter(mResultsAdapter);
    }

    public void onBackClick(View view){
        super.onBackPressed();
    }

    public void onShowClick(View view){
        mResultsAdapter = new ResultsAdapter(mGameState.getResults().getResultsList());
        mBinding.resultsList.setAdapter(mResultsAdapter);
    }

}

class ResultsAdapter extends BaseAdapter {
    private List<Quiz> mResults;
    private LayoutInflater mLayoutInflater;

    public ResultsAdapter(List<Quiz> results) {
        this.mResults = results;
    }

    @Override
    public int getCount() {
        return mResults.size();
    }

    @Override
    public Object getItem(int position) {
        return mResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View result = convertView;
        ResultListItemBinding binding;
        if (result == null) {
            if (mLayoutInflater == null) {
                mLayoutInflater = (LayoutInflater) parent.getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            binding = ResultListItemBinding.inflate(
                    mLayoutInflater, parent, false);
            result = binding.getRoot();
            result.setTag(binding);
        }
        else {
            binding = (ResultListItemBinding) result.getTag();
        }
        binding.setQuiz(mResults.get(position));
        return result;
    }
}
