package com.aallam.openai.api.completion

import com.aallam.openai.api.model.ModelId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A request for OpenAI to generate a predicted completion for a prompt.
 * All fields are Optional.
 *
 * [documentation](https://beta.openai.com/docs/api-reference/create-completion)
 */
@Serializable
class CompletionRequest(

    /**
     * ID of the model to use.
     */
    @SerialName("model") val model: ModelId,

    /**
     * What sampling temperature to use. Higher values means the model will take more risks.
     * Try 0.9 for more creative applications, and 0 (argmax sampling) for ones with a well-defined answer.
     *
     * We generally recommend using this or [topP] but not both.
     *
     * Defaults to 1.
     */
    @SerialName("temperature") val temperature: Double? = null,

    /**
     * An alternative to sampling with temperature, called nucleus sampling, where the model considers the results of
     * the tokens with top_p probability mass. So 0.1 means only the tokens comprising the top 10% probability mass are
     * considered.
     *
     * We generally recommend using this or [temperature] but not both.
     *
     * Defaults to 1.
     */
    @SerialName("top_p") val topP: Double? = null,

    /**
     * Include the log probabilities on the [logprobs] most likely tokens, as well the chosen tokens.
     * For example, if [logprobs] is 10, the API will return a list of the 10 most likely tokens.
     * The API will always return the logprob of the sampled token, so there may be up to [logprobs]+1 elements
     * in the response.
     *
     * Defaults to `null`.
     */
    @SerialName("logprobs") val logprobs: Int? = null,

    /**
     * Echo back the prompt in addition to the completion.
     *
     * Defaults to `false`.
     */
    @SerialName("echo") val echo: Boolean? = null,
)
