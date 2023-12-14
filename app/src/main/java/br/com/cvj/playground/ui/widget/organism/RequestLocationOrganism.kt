package br.com.cvj.playground.ui.widget.organism

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import br.com.cvj.playground.R
import br.com.cvj.playground.ui.theme.Colors
import br.com.cvj.playground.ui.widget.atom.BodyTextAtom
import br.com.cvj.playground.ui.widget.molecule.ButtonMolecule


@Composable
fun RequestLocationOrganism(
    modifier: Modifier = Modifier,
    @StringRes description: Int,
    @DrawableRes image: Int,
    @StringRes btnText: Int = R.string.activity_permission_request_btn,
    onClick: () -> Unit
) {

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(Colors.Blue900)
            .padding(vertical = 64.dp, horizontal = 16.dp)
    ) {

        val (imageContainer, text, button) = createRefs()
        createVerticalChain(imageContainer, text, button, chainStyle = ChainStyle.Spread)

        Box(
            modifier
                .constrainAs(imageContainer) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .width(128.dp)
                .height(128.dp)) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "",
            )
        }

        BodyTextAtom(
            modifier = Modifier
                .constrainAs(text) {
                    top.linkTo(imageContainer.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(button.top)
                }
                .padding(top = 16.dp),
            text = stringResource(id = description)
        )

        ButtonMolecule(
            modifier = Modifier
                .constrainAs(button) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                }
                .fillMaxWidth(),
            text = stringResource(id = btnText),
            onClick = onClick
        )
    }

}


@Composable
@Preview
fun RequestLocationOrganismPreview() {
    RequestLocationOrganism(
        description = R.string.activity_permission_location_info_text,
        image = R.drawable.img_location,
        btnText = R.string.activity_permission_location_request_btn,
        onClick = {}
    )
}

@Composable
@Preview
fun RequestPhoneOrganismPreview() {
    RequestLocationOrganism(
        description = R.string.activity_permission_phone_info_text,
        image = R.drawable.img_phone_permission,
        onClick = {}
    )
}