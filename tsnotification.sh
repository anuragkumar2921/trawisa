#!/bin/bash

# Fetch the necessary details
COMMITTER_NAME=$(git log -1 --pretty=format:'%an')
COMMIT_MESSAGE=$(git log -1 --pretty=format:'%s')
REPO_NAME=$BITBUCKET_REPO_FULL_NAME
BITBUCKET_BRANCH=$BITBUCKET_BRANCH

# Prepare the JSON payload for MS Teams
read -r -d '' PAYLOAD << EOM
{
    "@type": "MessageCard",
    "@context": "http://schema.org/extensions",
    "themeColor": "0076D7",
    "summary": "$COMMITTER_NAME committed to $REPO_NAME",
    "sections": [{
        "activityTitle": "$COMMITTER_NAME committed to $REPO_NAME",
        "activitySubtitle": "Information Card",
        "activityImage": "https://shellijohnson.com/wp-content/uploads/2019/01/commitlikeaboss_shellijohnson.jpg",
        "facts": [{
            "name": "Committer",
            "value": "$COMMITTER_NAME"
        }, {
            "name": "Repository",
            "value": "$REPO_NAME"
        }, {
            "name": "Branch",
            "value": "$BITBUCKET_BRANCH"
        }, {
            "name": "Commit Message",
            "value": "$COMMIT_MESSAGE"
        }],
        "markdown": true
    }]
}
EOM
# Send the notification
curl -d "$PAYLOAD" -H "Content-Type:application/json" https://applaunch1.webhook.office.com/webhookb2/80b6cd02-25b6-4112-8a06-f3470127e1b0@87c11092-5493-4daf-ae33-34615092d2d1/IncomingWebhook/e98e4ee61e994acd8f8a5e58c75e6eae/d2f7a16d-0c13-4d3a-bdd6-073a258cc9b9
# Prepare the JSON payload for MS Teams
read -r -d '' PAYLOAD2 << EOM
{
    "committerName": "$COMMITTER_NAME",
    "repoName": "$REPO_NAME",
    "branchName": "$BITBUCKET_BRANCH"
}
EOM

# Add to Google Sheet
curl -d "$PAYLOAD2" -H "Content-Type:application/json" https://script.google.com/macros/s/AKfycby9JGi6yOGGHWww-4sHZkeKW10TQDPtoS4ywId_ovhWz7PtT83fF5Q-4pjj7tqYhn1b/exec