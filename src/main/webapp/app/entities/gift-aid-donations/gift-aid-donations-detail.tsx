import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './gift-aid-donations.reducer';
import { IGiftAidDonations } from 'app/shared/model/gift-aid-donations.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGiftAidDonationsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class GiftAidDonationsDetail extends React.Component<IGiftAidDonationsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { giftAidDonationsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="accounTalkApp.giftAidDonations.detail.title">GiftAidDonations</Translate> [
            <b>{giftAidDonationsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="charityName">
                <Translate contentKey="accounTalkApp.giftAidDonations.charityName">Charity Name</Translate>
              </span>
            </dt>
            <dd>{giftAidDonationsEntity.charityName}</dd>
            <dt>
              <span id="donationDate">
                <Translate contentKey="accounTalkApp.giftAidDonations.donationDate">Donation Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={giftAidDonationsEntity.donationDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="donationAmount">
                <Translate contentKey="accounTalkApp.giftAidDonations.donationAmount">Donation Amount</Translate>
              </span>
            </dt>
            <dd>{giftAidDonationsEntity.donationAmount}</dd>
            <dt>
              <span id="giftAidClaimed">
                <Translate contentKey="accounTalkApp.giftAidDonations.giftAidClaimed">Gift Aid Claimed</Translate>
              </span>
            </dt>
            <dd>{giftAidDonationsEntity.giftAidClaimed ? 'true' : 'false'}</dd>
            <dt>
              <Translate contentKey="accounTalkApp.giftAidDonations.taxReturn">Tax Return</Translate>
            </dt>
            <dd>{giftAidDonationsEntity.taxReturn ? giftAidDonationsEntity.taxReturn.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/gift-aid-donations" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/gift-aid-donations/${giftAidDonationsEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ giftAidDonations }: IRootState) => ({
  giftAidDonationsEntity: giftAidDonations.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(GiftAidDonationsDetail);
